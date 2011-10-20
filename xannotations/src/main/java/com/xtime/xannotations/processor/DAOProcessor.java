package com.xtime.xannotations.processor;

import com.xtime.xannotations.DAO;

import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * $Id: //development/xtime/trunk/xannotations/src/main/java/com/xtime/xannotations/processor/DAOProcessor.java#1 $
 * $Author: savadhanula $
 * $Id: //development/xtime/trunk/xannotations/src/main/java/com/xtime/xannotations/processor/DAOProcessor.java#1 $
 * <p/>
 * Description goes here
 */


@SupportedSourceVersion(SourceVersion.RELEASE_6)
//@MetaInfServices(value= Processor.class)
public class DAOProcessor extends AbstractCapClassProcessor{

    private static int len = "Home".length();

    private static String templateStr = "import java.util.List;import org.slf4j.Logger;" +
            "mport org.slf4j.LoggerFactory;import org.hibernate.LockMode;import static org.hibernate.criterion.Example.create;" +
            "import com.xtime.xannotations.DAO; public class $className$ extends com.xtime.persistence.hibernate.CRUDService" +
            " {private static final Logger log = LoggerFactory.getLogger($className$.class);public $className$(){}" +
            "public void persist($className$ transientInstance) {log.debug(\"persisting $className$ instance\");" +
            "try{getSession().persist(transientInstance); log.debug(\"persist successful\");}catch (RuntimeException re)" +
            "{log.error(\"persist failed\", re);throw re;}}public void attachDirty($className$ instance){log.debug(\"attaching dirty $className$ instance\");" +
            "try{getSession().saveOrUpdate(instance);log.debug(\"attach successful\");}catch (RuntimeException re){log.error(\"attach failed\", re);throw re;}}" +
            "public void attachClean($className$ instance) {log.debug(\"attaching clean $className$ instance\");try {getSession().lock(instance, LockMode.NONE);" +
            "log.debug(\"attach successful\");}catch (RuntimeException re) {log.error(\"attach failed\", re);throw re;}}" +
            "public void delete($className$ persistentInstance) {log.debug(\"deleting $className$ instance\");try {getSession().delete(persistentInstance);" +
            "log.debug(\"delete successful\");}catch (RuntimeException re) {log.error(\"delete failed\", re);throw re;}}" +
            "public $className$ merge($className$ detachedInstance) {log.debug(\"merging $className$ instance\");try " +
            "{$className$ result = ($className$) getSession().merge(detachedInstance);log.debug(\"merge successful\");return result;}" +
            "catch (RuntimeException re) {log.error(\"merge failed\", re);throw re;}}public $className$ findById( com.xtime.model.xddw.TDealerStoreId id) " +
            "{log.debug(\"getting $className$ instance with id: \" + id);try {$className$ instance = ($className$) getSession().get(id);if (instance==null) " +
            "{log.debug(\"get successful, no instance found\");}else {log.debug(\"get successful, instance found\");}return instance;}catch (RuntimeException re) " +
            "{log.error(\"get failed\", re);throw re;}}public List<$className$> findByExample($className$ instance) {log.debug(\"finding $className$ instance by example\");" +
            "try {List<$className$> results = (List<$className$) getSession().createCriteria(\"$className$\").add( create(instance) ).list();log.debug(\"find by example" +
            " successful, result size:\" + results.size());return results;}catch (RuntimeException re) {log.error(\"find by example failed\", re);throw re;}} }";




    public DAOProcessor()
    {
        super();

    }

     /*
     * Get supported annotationtypes
     * @return
     */
    @Override
    public Set<String> getSupportedAnnotationTypes() {
         Set<String> supportedAnnotationTypes = new HashSet<String>();
        supportedAnnotationTypes.add(DAO.class.getName());
        return supportedAnnotationTypes;
    }


    /**
     * Remove "Base" from the class name to get cap class name
     * @param element
     * @return
     */
    protected String getCapClassName( Element element)
    {
        return element.getSimpleName().toString()+"DAO";
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
              processingEnv.getMessager().printMessage(Diagnostic.Kind.NOTE, "working on "+ element.getSimpleName().toString());
              if (element.getSimpleName().toString().endsWith("DAO"))
                  continue;
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

                  //if (templateStr != null)
//                      sb.append(templateStr.replaceAll("$className", capClassName));

                  javaFileObject.openWriter().append(sb.toString()).close();
              }
              catch (IOException ioe)
              {
                  processingEnv.getMessager().printMessage(Diagnostic.Kind.ERROR, "exception "+ ioe.toString());
              }
              finally {

              }
          }
      }



}
