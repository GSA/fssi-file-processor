package main.java.gov.gsa.fssi.files.sourceFiles.utils.contexts;

import main.java.gov.gsa.fssi.files.sourceFiles.SourceFile;
import main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies.SourceFileExporterStrategy;

public class SourceFileExporterContext {
	   private SourceFileExporterStrategy strategy;   

	   //this can be set at runtime by the application preferences
	   public void setSourceFileExporterStrategy(SourceFileExporterStrategy strategy){
	       this.strategy = strategy;  
	   }

	   //this can be set at runtime by the application preferences
	   public SourceFileExporterStrategy getSourceFileExporterStrategy(){
	       return this.strategy;
	   }
	   
	  //use the strategy
		/**
		 * @param field
		 * @param constraint
		 * @param data
		 */
		public void export(String directory, SourceFile sourceFile) {
			strategy.export(directory, sourceFile);
	   }
}
