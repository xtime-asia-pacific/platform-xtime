package com.xtime.xannotations.processor;


import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * $Id: //development/xtime/trunk/xannotations/src/main/java/com/xtime/xannotations/processor/AbstractCapClassProcessor.java#2 $
 * $Author: savadhanula $
 * $Id: //development/xtime/trunk/xannotations/src/main/java/com/xtime/xannotations/processor/AbstractCapClassProcessor.java#2 $
 * <p/>
 * Description goes here
 */

public abstract class AbstractCapClassProcessor extends AbstractProcessor{

    private List<Element> processingElements = new ArrayList<Element>();

    @Override
    public Set<String> getSupportedOptions() {
        return super.getSupportedOptions();

    }



    @Override
    public void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
    }


    @Override
    public boolean process(Set<? extends TypeElement> typeElements, RoundEnvironment roundEnvironment) {
        if (!roundEnvironment.processingOver())
        {
            for (String annotationType: getSupportedAnnotationTypes())
            {
                //Set<? extends Element> annotatedElements = roundEnvironment.getRootElements();
                Set<? extends Element> annotatedElements = roundEnvironment.getElementsAnnotatedWith(processingEnv.getElementUtils().getTypeElement(annotationType));

                for (Element annotatedElement: annotatedElements)
                {
                    if (annotatedElement.getKind() != ElementKind.CLASS)
                        continue;
                    processingElements.add(annotatedElement);
                }

            }
        }
        else
        {
            generateCapClasses(processingElements);
        }
        return false;
    }


    protected List<Element> getProcessingElements()
    {
        return this.processingElements;
    }


    /**
     *
      * @param elements
     */
   protected void generateCapClasses(List<Element> elements)
    {
        if (elements == null || elements.isEmpty())
        {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING,"Empty/Null elements passed");
            return;
        }

        for (Element element: elements)
        {
            generateCapClasses(element);
        }

    }

    protected void generateCapClasses (Element element)
    {
          String capClassName = getCapClassName(element);
            String fullyQualifiedClassName = element.asType().toString();
            String packageName = element.getEnclosingElement().asType().toString();

            String capFile = packageName+"."+capClassName;
            processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "creating file : "+ capFile);
            JavaFileObject javaFileObject = null;

            try
            {
                javaFileObject = processingEnv.getFiler().createSourceFile(capFile);
                StringBuffer sb = new StringBuffer();
                sb.append("package "+ packageName+";");
                sb.append("\n\n/**\n * Automatically generated file. All modifications to this will be lost \n **/\n\n\n");
                sb.append("\nimport com.xtime.xannotations.DAO;\n\n");
                sb.append("\nimport javax.xml.bind.annotation.XmlRootElement;\n\n");
                sb.append("\n@DAO\n@XmlRootElement\n");
                sb.append("\n\npublic class "+ capClassName+" extends "+ fullyQualifiedClassName);
                sb.append("{\n\n \t public "+ capClassName+"(){\n\t\t super();\n\t}\n\n\n}");
                javaFileObject.openWriter().append(sb.toString()).close();
            }
            catch (IOException ioe)
            {}
            finally {

            }
       }


    /**
     * Get CapClassName for the given element
     * @param element
     * @return
     */
    abstract protected String getCapClassName(Element element);


}
