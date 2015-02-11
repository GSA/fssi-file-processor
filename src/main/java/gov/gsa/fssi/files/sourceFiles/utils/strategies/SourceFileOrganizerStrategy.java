package main.java.gov.gsa.fssi.files.sourceFiles.utils.strategies;

import main.java.gov.gsa.fssi.files.sourceFiles.SourceFile;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author DavidKLarrimore
 *
 */
public interface SourceFileOrganizerStrategy {
	Logger logger = LoggerFactory.getLogger(SourceFileOrganizerStrategy.class);

	void organize(SourceFile sourceFile);
}
