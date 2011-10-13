package com.xtime.xannotations.processor;

import com.xtime.xannotations.DataModel;

import javax.annotation.processing.*;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic.Kind;
import javax.lang.model.element.TypeElement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;

import org.kohsuke.MetaInfServices;

/**
 * $Id: //development/xtime/trunk/xannotations/src/main/java/com/xtime/xannotations/processor/DataModelProcessor.java#1 $
 * $Author: savadhanula $
 * <p/>
 *  Process DataModel Annotation
 */


// @SupportedSourceVersion(SourceVersion.RELEASE_6)
@MetaInfServices(Processor.class)
public class DataModelProcessor extends AbstractProcessor{


    private List<Element> processingElement = new ArrayList<Element>();

    public DataModelProcessor()
    {}


    @Override
    public Set<String> getSupportedOptions() {
        return super.getSupportedOptions();

    }

    /**
     * Get supported annotationtypes
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
         Set<String> supportedAnnotationTypes = new HashSet<String>();
        supportedAnnotationTypes.add(DataModel.class.getName());
        return supportedAnnotationTypes;
    }

    @Override
    public void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        processingEnv.getMessager().printMessage(Kind.NOTE," INIT..");
    }

    @Override
    public boolean process(Set<? extends TypeElement> typeElements, RoundEnvironment roundEnvironment) {
        if (!roundEnvironment.processingOver())
        {
            for (String annotationType: getSupportedAnnotationTypes())
            {
                Set<? extends Element> annotatedElements = roundEnvironment.getElementsAnnotatedWith(processingEnv.getElementUtils().getTypeElement(annotationType));
                for (Element annotatedElement: annotatedElements)
                    processingElement.add(annotatedElement);

            }
        }
        else
        {
            processingEnv.getMessager().printMessage(Kind.NOTE,"Added the following elements");
           for (Element annotatedElement: processingElement)
           {
               processingEnv.getMessager().printMessage(Kind.NOTE,"*", annotatedElement);
           }
            processingEnv.getMessager().printMessage(Kind.NOTE,"---------");
        }
        return false;
    }

}
