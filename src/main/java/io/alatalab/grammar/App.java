package io.alatalab.grammar;

import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.simple.Sentence;

import java.util.*;

public class App 
{
    public static void main( String[] args )
    { 
    	
        String text = "This quilted puffer vest is lined with fleece and has a detachable hood";

    	extractDependencies(text);
    }
    
    
    public static void extractDependencies(String text){
        // creates a StanfordCoreNLP object, with POS tagging, lemmatization, NER, parsing, and coreference resolution
      //  Properties props = new Properties();
     //   props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
       // StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        // create an empty Annotation just with the given text
       // Annotation document = new Annotation(text);

        // run all Annotators on this text
        //pipeline.annotate(document);
    	long time=System.currentTimeMillis();
        Sentence sentence = new Sentence(text);
         Collection<RelationTriple> triples= sentence.openieTriples();
         for (RelationTriple triple: triples){
        	 System.out.println("object:" +triple.objectHead()+",subject:"+triple.subjectHead()+",confidence:"+triple.confidence);
         }
         System.out.println("take time:"+(System.currentTimeMillis()-time)/1000+ " sec");
    }
}
