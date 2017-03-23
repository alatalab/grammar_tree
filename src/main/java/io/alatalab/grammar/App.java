package io.alatalab.grammar;

import edu.stanford.nlp.ie.util.RelationTriple;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.*;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.simple.Sentence;
import edu.stanford.nlp.trees.GrammaticalRelation;
import edu.stanford.nlp.trees.Tree;

import java.util.*;

public class App {
	public static void main(String[] args) {
		String text =  //"The quick brown fox jumps over the lazy dog";
				// "This quilted puffer vest is lined with fleece and has a
				// detachable hood";
				"As he crossed toward the pharmacy at the corner he involuntarily turned his head because of a burst of light that had ricocheted from his temple, and saw, with that quick smile with which we greet a rainbow or a rose, a blindingly white parallelogram of sky being unloaded from the van—a dresser with mirrors across which, as across a cinema screen, passed a flawlessly clear reflection of boughs sliding and swaying not arboreally, but with a human vacillation, produced by the nature of those who were carrying this sky, these boughs, this gliding façade.";

		if (args != null && args.length == 1 && args[0] != null && args[0].length() > 1)
			text = args[0];

		extractDependencies(text);
	}

	public static void extractDependencies(String text) {
		// creates a StanfordCoreNLP object, with POS tagging, lemmatization,
		// NER, parsing, and coreference resolution
		// Properties props = new Properties();
		// props.setProperty("annotators", "tokenize, ssplit, pos, lemma, ner,
		// parse, dcoref");
		// StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

		// create an empty Annotation just with the given text
		// Annotation document = new Annotation(text);

		// run all Annotators on this text
		// pipeline.annotate(document);
		long time = System.currentTimeMillis();
		Sentence sentence = new Sentence(text);
		Collection<RelationTriple> triples = sentence.openieTriples();
		for (RelationTriple triple : triples) {
			if (triple.confidence >.999) {
				System.out.println("relation:" + triple);

				// System.out.println("object:"
				// +triple.objectHead()+",subject:"+triple.subjectHead()+",confidence:"+triple.confidence);

			}
		}
		SemanticGraph graph = sentence.dependencyGraph();
		System.out.println(graph);
		SemanticGraph new_graph = graph.makeSoftCopy();
		Collection<IndexedWord> roots = graph.getRoots();
		for (IndexedWord root : roots) {
			if (root.tag().equals("VBZ")) {
				
				for(IndexedWord child:graph.getChildren(root)){
					Set<GrammaticalRelation> relations = graph.relns(child);
					for (GrammaticalRelation relation:relations){
						if(relation.getShortName().equals("nsubj")){
							new_graph.resetRoots();
							new_graph.addRoot(new_graph.getChildWithReln(root, relation));
							new_graph.removeVertex(child);
							}
					}
					//if(child.tag().equals ("NN")) graph.setRoot(child);
				}
				
			}
		}
System.out.println(new_graph);		
Tree tree = sentence.parse();

		System.out.println(tree);
		System.out.println("take time:" + (System.currentTimeMillis() - time) / 1000 + " sec");
	}
}
