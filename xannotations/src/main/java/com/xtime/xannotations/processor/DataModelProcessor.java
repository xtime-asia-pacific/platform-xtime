package com.xtime.xannotations.processor;

import com.xtime.xannotations.DataModel;
import org.kohsuke.MetaInfServices;

import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import java.util.HashSet;
import java.util.Set;



/**
 * $Id: //development/xtime/trunk/xannotations/src/main/java/com/xtime/xannotations/processor/DataModelProcessor.java#4 $
 * $Author: savadhanula $
 * <p/>
 *  Process DataModel Annotation
 */


@SupportedSourceVersion(SourceVersion.RELEASE_6)
@MetaInfServices(value=Processor.class)
public class DataModelProcessor extends AbstractCapClassProcessor{

    private static int len = "Base".length();

    public DataModelProcessor()
    {
        super();
    }

     @Override
    public void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
    }

    /*
     * Get supported annotationtypes
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
         Set<String> supportedAnnotationTypes = new HashSet<String>();
        supportedAnnotationTypes.add(DataModel.class.getName());
        return supportedAnnotationTypes;
    }


    /**
     * Remove "Base" from the class name to get cap class name
     * @param element
     * @return
     */
    protected String getCapClassName( Element element)
    {
        return element.getSimpleName().toString().substring(len);
    }
}
