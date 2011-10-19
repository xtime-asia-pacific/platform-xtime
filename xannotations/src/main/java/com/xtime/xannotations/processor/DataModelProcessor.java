package com.xtime.xannotations.processor;

import com.xtime.xannotations.DataModel;
import org.kohsuke.MetaInfServices;
import sun.misc.IOUtils;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



/**
 * $Id: //development/xtime/trunk/xannotations/src/main/java/com/xtime/xannotations/processor/DataModelProcessor.java#2 $
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
        processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE,"getting cap class for "+ element.asType().toString());
        return element.getSimpleName().toString().substring(len);
    }
}
