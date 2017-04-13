package textgen;

import java.util.LinkedList;
import java.util.List;
import java.util.ListIterator;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ArrayList;

/**
 * An implementation of the MTG interface that uses a list of lists.
 * @author UC San Diego Intermediate Programming MOOC team
 */
public class MarkovTextGeneratorLoL implements MarkovTextGenerator {

	// The list of words with their next words
	private List<ListNode> wordList;

	// The starting "word"
	private String starter;

	// The random number generator
	private Random rnGenerator;

	public MarkovTextGeneratorLoL(Random generator)
	{
		wordList = new LinkedList<ListNode>();
		starter = "";
		rnGenerator = generator;
	}


	/** Train the generator by adding the sourceText */
	@Override
	public void train(String sourceText)
	{
		// TODO: Implement this method
		starter = "";
		String[] words = sourceText.split(" ");
		int lastIndex = words.length-1;
		int index = 0;
		for(String word : words)
		{
			if(doesStarterExists(starter))
			{
				addtoNextWord(starter,word);
			}
			else
			{
				ListNode newNode = new ListNode(starter);
				newNode.addNextWord(word);
				wordList.add(newNode);
			}
			starter = word;
			if(index == lastIndex)
			{
				String lastValue = words[words.length -1];
				addtoNextWord(lastValue, " ");
			}
		}

	}

	/**
	 * Generate the number of words requested.
	 */
	@Override
	public String generateText(int numWords) {
	    // TODO: Implement this method
		starter = "";
		List<String> output = new ArrayList<String>();
		String finalValue = "";
		int i = 0;
		try
		{
			while(i < numWords)
			{
				ListNode node_Starter = getStarterNode(starter);
				if(node_Starter == null)
				{
					node_Starter = getStarterNode("");
				}

				String randomWord = node_Starter.getRandomNextWord(rnGenerator);
				output.add(randomWord);
				starter = randomWord;
				i++;
			}
			for(String word : output)
			{
				finalValue += word + " ";
			}
		}
		catch(Exception ex)
		{

		}
		return finalValue;
	}


	// Can be helpful for debugging
	@Override
	public String toString()
	{
		String toReturn = "";
		for (ListNode n : wordList)
		{
			toReturn += n.toString();
		}
		return toReturn;
	}

	/** Retrain the generator from scratch on the source text */
	@Override
	public void retrain(String sourceText)
	{
		// TODO: Implement this method.
		starter = "";
		wordList = new LinkedList<ListNode>();
		rnGenerator = new Random(42);

		String[] words = sourceText.split(" ");
		int lastIndex = words.length-1;
		int index = 0;
		for(String word : words)
		{
			if(doesStarterExists(starter))
			{
				addtoNextWord(starter,word);
			}
			else
			{
				ListNode newNode = new ListNode(starter);
				newNode.addNextWord(word);
				wordList.add(newNode);
			}
			starter = word;
			if(index == lastIndex)
			{
				String lastValue = words[words.length -1];
				addtoNextWord(lastValue, "");
			}
		}
	}

	// TODO: Add any private helper methods you need here.
	private List<String> getWords(String pattern, String text)
	{
		List<String> words = new ArrayList<String>();
		Pattern tokSplitter = Pattern.compile(pattern);
		Matcher match = tokSplitter.matcher(text);
		while(match.find())
		{
			words.add(match.group());
		}
		return words;
	}

	private boolean doesStarterExists(String starterWord)
	{
		boolean isExists = false;
		for(ListNode lNode : wordList)
		{
			if(lNode.getWord().equals(starterWord))
			{
				isExists = true;
				break;
			}
		}
		return isExists;
	}

	private void addtoNextWord(String starterWord, String toBeAdded)
	{
		for(ListNode lNode : wordList)
		{
			if(lNode.getWord().equals(starterWord))
			{
				lNode.addNextWord(toBeAdded);
			}
		}
	}

	private ListNode getStarterNode(String starter)
	{
		for(ListNode lNode : wordList)
		{
			if(lNode.getWord().equals(starter))
			{
				return lNode;
			}
		}
		return null;
	}
	/**
	 * This is a minimal set of tests.  Note that it can be difficult
	 * to test methods/classes with randomized behavior.
	 * @param args
	 */
	public static void main(String[] args)
	{
		// feed the generator a fixed random value for repeatable behavior
		MarkovTextGeneratorLoL gen = new MarkovTextGeneratorLoL(new Random(42));
		//String textString = "Hello.  Hello there.  This is a test.  Hello there.  Hello Bob.  Test again.";
		//textString = "hi there hi Leo";
		//System.out.println(textString);
		//gen.train(textString);
		//System.out.println(gen);
		//System.out.println(gen.generateText(20));
		String textString2 = "You say yes, I say no, "+
				"You say stop, and I say go, go, go, "+
				"Oh no. You say goodbye and I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"I say high, you say low, "+
				"You say why, and I say I don't know. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"Why, why, why, why, why, why, "+
				"Do you say goodbye. "+
				"Oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello. "+
				"You say yes, I say no, "+
				"You say stop and I say go, go, go. "+
				"Oh, oh no. "+
				"You say goodbye and I say hello, hello, hello. "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello, "+
				"I don't know why you say goodbye, I say hello, hello, hello,";
		System.out.println(textString2);
		//gen.retrain(textString2);
		//System.out.println(gen);
		System.out.println(gen.generateText(20));
	}

}

/** Links a word to the next words in the list
 * You should use this class in your implementation. */
class ListNode
{
    // The word that is linking to the next words
	private String word;

	// The next words that could follow it
	private List<String> nextWords;

	ListNode(String word)
	{
		this.word = word;
		nextWords = new LinkedList<String>();
	}

	public String getWord()
	{
		return word;
	}

	public void addNextWord(String nextWord)
	{
		nextWords.add(nextWord);
	}

	public String getRandomNextWord(Random generator)
	{
		// TODO: Implement this method
	    // The random number generator should be passed from
	    // the MarkovTextGeneratorLoL class
		int indexValue = generator.nextInt(nextWords.size());
		String randomWord = nextWords.get(indexValue);
	    return randomWord;
	}

	public String toString()
	{
		String toReturn = word + ": ";
		for (String s : nextWords) {
			toReturn += s + "->";
		}
		toReturn += "\n";
		return toReturn;
	}

}


