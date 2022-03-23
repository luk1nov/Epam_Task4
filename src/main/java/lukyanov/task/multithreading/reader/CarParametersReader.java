package lukyanov.task.multithreading.reader;

import lukyanov.task.multithreading.exception.CustomException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

public class CarParametersReader {
    private static final Logger logger = LogManager.getLogger();

    public List<String> readParameters(String filepath) throws CustomException {
        List<String> params;
        try(BufferedReader reader = new BufferedReader(new FileReader(filepath))){
            params = reader.lines().toList();
        } catch (FileNotFoundException e) {
            logger.error("file " + filepath + " not found");
            throw new CustomException("file " + filepath + " not found");
        } catch (IOException e) {
            logger.error("reading error");
            throw new CustomException("reading error");
        }
        return params;
    }
}
