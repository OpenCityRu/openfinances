package org.openbudget.converter;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;

import org.openbudget.converter.face.BudgetFileReader;
import org.openbudget.converter.face.BudgetItemPrototype;
import org.openbudget.converter.face.ModelsCreator;
import org.openbudget.converter.face.Saver;
import org.openbudget.exception.CantReadFileConverterException;
import org.openbudget.exception.ConverterException;
import org.openbudget.exception.InputSettingsException;
import org.openbudget.exception.StandardConverterException;
import org.openbudget.model.BudgetItem;
import org.openbudget.model.GlobalSettings;
import org.openbudget.model.InputSettings;
import org.openbudget.model.MetaData;
import org.openbudget.model.SourceTable;
import org.openbudget.russia.converter.impl.SpendingTypeCreator;
import org.openbudget.russia.model.SpendingType;
import org.openbudget.utils.ConverterUtils;

/**
 * Base class Converter doesn't have realization. All specific converters must have own realization.
 * 
 * There are 4 roles for working with OBFCOnverter:
 * <ul>
 * <li>client</li> - who use a program (mostly not programmer).
 * <li>user</li> - who create and use Runners (also could be a programmer).
 * <li>developer</li> - who developed extensions for OBF (for example Russian).
 * <li>core team</li> - who developed core lib.
 * </ul> 
 * 
 * @author inxaoc
 *
 */
abstract public class OBFConverter<T extends BudgetItem, M extends MetaData> {
	
	protected static GlobalSettings globalSettings;

	//interfaces
	protected BudgetFileReader fileReader;
	protected BudgetItemPrototype prototype;
	protected ArrayList<ModelsCreator> modelsCreators;
	protected Saver saver;
	
	/**
	 * Each new instance must have settings with input params (got from user).
	 */
	protected InputSettings settings;
	protected InputStream stream;
	protected SourceTable matrix;
	protected ArrayList<T> budgetItems;
	protected M metadata;
	
	public OBFConverter(GlobalSettings globalSettings) throws ConverterException{
		
		//static
		OBFConverter.globalSettings = globalSettings;
		
	}
	
	public void initiate(InputSettings settings,
			BudgetFileReader fileReader,
			BudgetItemPrototype prototype,
			Saver saver) throws ConverterException{
		
		//temporal
		this.settings = settings;
		
		//interfaces
		this.fileReader = fileReader;
		this.prototype = prototype;
		this.saver = saver;
		
		//update prototype
		BudgetItem item = prototype.getOriginalItem();
		
		item.setDate(new Date());
		item.setPeriod(ConverterUtils.getDateFromPeriod(settings.getPeriod()));
		item.setLevel(settings.getLevel());
		item.setStage(settings.getStage());
		item.setType(settings.getType());
		
	}
	
	public void addModelCreatorSample(ModelsCreator creator){
		if(modelsCreators == null){
			modelsCreators = new ArrayList<ModelsCreator>();
		}
		modelsCreators.add(creator);
	}
	
	public static GlobalSettings getGlobalSettings() throws StandardConverterException{
		if(globalSettings == null){
			throw new StandardConverterException("Global settings are null. You should create new Instance of OBFConverter first then create InputSettings and then call initiate() method.");
		}
		return globalSettings;
	}
	

	/**
	 * this method is fully responsible for converting and includes 5 stages:
	 * <ul>
	 * <li><strong>read file</strong> - read file and check errors. 
	 * <b>How to use beforeReadFile() method?</b> 
	 * E.g. developer can change mechanism of getting/uploading file.</li>
	 * 
	 * <li><strong>create source table and models</strong> - prepare data for parsing (create matrix of source table) and converting matrix into objects (models). 
	 * <b>How to use beforeConverting() method?</b>
	 * For example developer can add some actions that can improve source file and put it into a stream before using BudgetItemReader implementation.</li>
	 * 
	 * <li><strong>create budget items</strong> - Call abstract createBudgetItems() (which should be realized in a child of OBFConverter).  
	 * It includes creating raw objects and add specific models in each BudgetItem (without populating standard values, ids, versions).</li>
	 * <b>How to use converting() method?</b> Make changes in Source Table.
	 * 
	 * <li><strong>finalize budget items</strong> - finalization of converting process and prepare for saving in output format. 
	 * It includes populating standard values, id, versions, creating metadata.</li>
	 * <b>How to use converting() method?</b> Introduce some actions for making information about id, versions are more correct. 
	 * 
	 * <li><strong>save</strong> - move data from objects into file in output format</li>
	 * On this step models are ready to save. 
	 * <b>How to use save() method?</b> Make some changes in models or metadata before it will be stored finally.
	 * 
	 * </ul> 
	 * It also for child classes allows to insert additional functions that could be executed before starting stage.
	 * All these abstract functions must be realized in child class but could be empty. 
	 * 
	 * This method could not be overrided. 
	 * 
	 * @throws ConverterException 
	 */
	public final void convert() throws ConverterException{
		
		//stage 1: preparation
		
		//developer can create own "beforeReadFile" additional method that could be invoked before reading stream
		beforeReadFile(); 
		//... and now actions
		
		// check file
		try {

			stream = new FileInputStream(settings.getSourceFilePath());

		} catch (IOException e) {
			throw new CantReadFileConverterException(settings.getSourceFilePath());
		}
		
		//stage 2: actions before converting
		
		//developer can create "before" method
		beforeCreateSourceTable();
		//... and now standard actions (correct for all OBF converters that match requirements)
		
		matrix = fileReader.createSourceTable(stream);
		
		for(ModelsCreator model : modelsCreators){
			
			ArrayList<ModelsCreator> models = model.createModels(matrix);
			model.saveModels(models);
			
		}
		
		//stage 3: converting
		
		//developer can create own "converting actions" 
		beforeCreateBudgetItems();
		//... and now actions (correct for all OBF converters that match requirements)
		
		
		budgetItems = createBudgetItems();
		
		//stage 4: actions after converting

		//developer can create own "after" 
		afterCreateBudgetItems();
		//... and now actions (correct for all OBF converters that match requirements)
		
		for(T item : budgetItems){
			
			prototype.copyValuesIn(item);
			
			setVersions(item);
			
			setId(item);
			
		}
		
		//metadata = createMetaData();

		//stage 5: save
		
		//developer can create own "save" additional method
		
		beforeSave();
		//... and now standard actions (correct for all OBF converters that match requirements)
		
		saver.save(budgetItems, metadata, settings.getOutputFileName());
		
	}

	/**
	 * 
	 * @return
	 * @throws ConverterException 
	 */
	abstract protected M createMetaData() throws ConverterException;

	/**
	 * Populate unique id
	 * @param item
	 */
	abstract protected void setId(T item) throws ConverterException;

	/**
	 * Set versions
	 * @param item
	 */
	abstract protected void setVersions(T item) throws ConverterException;

	/**
	 * Creating BudgetItems from Models and Source Table.
	 * This method must be realized.
	 * @return
	 * @throws ConverterException 
	 */
	abstract protected ArrayList<T> createBudgetItems() throws ConverterException;

	/**
	 * Overriding this method is optional and don't need to be done in general cases.
	 * Some actions could be executed in child class before starting this process (stage 1: read file) in base class.
	 * This method will be invoked before this process in parent class is started.
	 */
	abstract protected void beforeReadFile();
	
	/**
	 * Overriding this method is optional and don't need to be done in general cases.
	 * Some actions could be executed in child class before starting this process (stage 2: Create source model) in base class
	 * This method will be invoked before this process in parent class is started.
	 * @throws ConverterException 
	 */
	abstract protected void beforeCreateSourceTable() throws ConverterException;
	
	/**
	 * Some actions could be executed in child class before starting this process (stage 3: CreateBudgetItems) in base class
	 * This method will be invoked before this process in parent class is started.
	 */
	abstract protected void afterCreateBudgetItems();
	
	/**
	 * Overriding this method is optional and don't need to be done in general cases.
	 * Some actions could be executed in child class before starting this process (stage 4: actions after converting) in base class
	 * This method will be invoked before this process in parent class is started.
	 * @throws ConverterException 
	 */
	abstract protected void beforeCreateBudgetItems() throws ConverterException;
	
	/**
	 * Overriding this method is optional and don't need to be done in general cases.
	 * Some actions could be executed in child class before starting this process (stage 5: save) in base class
	 * This method will be invoked before this process in parent class will be started.
	 */
	abstract protected void beforeSave();
	
	/**
	 * User and developer can get it.
	 * @return
	 */
	public InputSettings getSettings() {
		return settings;
	}
	
	public void getSettings(InputSettings settings) {
		this.settings = settings;
	}

	/**
	 * User and developer can get it. User should choose an appropriate reader for 
	 * @return
	 */
	public BudgetFileReader getFileReader() {
		return fileReader;
	}

	/**
	 * User and developer can add new interface.
	 * @param fileReader
	 */
	public void setFileReader(BudgetFileReader fileReader) {
		this.fileReader = fileReader;
	}

	/**
	 * User and developer can get it.
	 * @return
	 */
	public BudgetItemPrototype getPrototype() {
		return prototype;
	}

	/**
	 * User and developer can add new interface.
	 * @param prototype
	 */
	public void setPrototype(BudgetItemPrototype prototype) {
		this.prototype = prototype;
	}

	/**
	 * User and developer can get it.
	 * @return
	 */
	public ArrayList<ModelsCreator> getModelsCreators() {
		return modelsCreators;
	}

	/**
	 * User and developer can add new interface.
	 * @param modelsCreators
	 */
	public void setModelsCreators(ArrayList<ModelsCreator> modelsCreators) {
		this.modelsCreators = modelsCreators;
	}

	/**
	 * User and developer can get it.
	 * @return
	 */
	public Saver getSaver() {
		return saver;
	}

	/**
	 * User and developer can add new interface.
	 * @param saver
	 */
	public void setSaver(Saver saver) {
		this.saver = saver;
	}

	/**
	 * Protected because only developer can use it.
	 * @return
	 */
	protected InputStream getStream() {
		return stream;
	}

	/**
	 * This is super and not-safe feature. Protected because only developer can use it.
	 * @param stream
	 */
	protected void setStream(InputStream stream) {
		this.stream = stream;
	}

	/**
	 * Protected because only developer can use it.
	 * @return
	 */
	protected SourceTable getMatrix() {
		return matrix;
	}

	/**
	 * This is super and not-safe feature. Protected because only developer can use it.
	 * @param matrix
	 */
	protected void setMatrix(SourceTable matrix) {
		this.matrix = matrix;
	}

	/**
	 * Protected because only developer can use it.
	 * @return
	 */
	protected ArrayList<T> getBudgetItems() {
		return budgetItems;
	}

	/**
	 * This is super and not-safe feature. Protected because only developer can use it.
	 * @param budgetItems
	 */
	protected void setBudgetItems(ArrayList<T> budgetItems) {
		this.budgetItems = budgetItems;
	}

	/**
	 * Protected because only developer can add global settings.
	 * @param globalSettings
	 */
	protected static void setGlobalSettings(GlobalSettings globalSettings) {
		OBFConverter.globalSettings = globalSettings;
	}

	/**
	 * User can add new input settings.
	 * @param settings
	 */
	public void setSettings(InputSettings settings) {
		this.settings = settings;
	}
	
}
