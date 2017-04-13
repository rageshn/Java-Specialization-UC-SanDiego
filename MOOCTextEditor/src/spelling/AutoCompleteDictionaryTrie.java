package spelling;

import java.util.List;
import java.util.Queue;
import java.util.Set;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;

/**
 * An trie data structure that implements the Dictionary and the AutoComplete
 * ADT
 *
 * @author Ragesh
 *
 */
public class AutoCompleteDictionaryTrie implements Dictionary, AutoComplete {

	private TrieNode root;
	private int size;

	public AutoCompleteDictionaryTrie() {
		root = new TrieNode();
		size = 0;
	}

	/**
	 * Insert a word into the trie. For the basic part of the assignment (part
	 * 2), you should ignore the word's case. That is, you should convert the
	 * string to all lower case as you insert it.
	 */
	public boolean addWord(String word) {
		// TODO: Implement this method.
		String lower = word.toLowerCase();
		char[] characters = lower.toCharArray();
		int charLength = characters.length;
		TrieNode current = root;
		TrieNode currentNode = null;
		if (!isWord(lower)) {
			for (int i = 0; i < charLength; i++) {
				currentNode = current.insert(characters[i]);
				if (currentNode == null) {
					currentNode = current.getChild(characters[i]);
				}
				if (i == charLength - 1) {
					currentNode.setEndsWord(true);
					size++;
				}
				current = currentNode;
			}
			return true;
		}
		return false;
	}

	/**
	 * Return the number of words in the dictionary. This is NOT necessarily the
	 * same as the number of TrieNodes in the trie.
	 */
	public int size() {
		// TODO: Implement this method
		return size;
	}

	/** Returns whether the string is a word in the trie */
	@Override
	public boolean isWord(String s) {
		// TODO: Implement this method
		String lower = s.toLowerCase();
		char[] chars = lower.toCharArray();
		TrieNode current =root;
		for(char c : chars)
		{
			if(current != null)
			{
				current = current.getChild(c);
				if(current != null)
				{
					if(current.endsWord() && current.getText().equals(lower))
						return true;
				}
			}
		}
		return false;
	}

	/**
	 * * Returns up to the n "best" predictions, including the word itself, in
	 * terms of length If this string is not in the trie, it returns null.
	 *
	 * @param text
	 *            The text to use at the word stem
	 * @param n
	 *            The maximum number of predictions desired.
	 * @return A list containing the up to n best predictions
	 */
	@Override
	public List<String> predictCompletions(String prefix, int numCompletions) {
		// TODO: Implement this method
		// This method should implement the following algorithm:
		// 1. Find the stem in the trie. If the stem does not appear in the
		// trie, return an
		// empty list
		// 2. Once the stem is found, perform a breadth first search to generate
		// completions
		// using the following algorithm:
		// Create a queue (LinkedList) and add the node that completes the stem
		// to the back
		// of the list.
		// Create a list of completions to return (initially empty)
		// While the queue is not empty and you don't have enough completions:
		// remove the first Node from the queue
		// If it is a word, add it to the completions list
		// Add all of its child nodes to the back of the queue
		// Return the list of completions
		 List<String> temp = new LinkedList<String>();
		 TrieNode t = root;
		 int flag = 0;
		 char[] str = (prefix.toLowerCase()).toCharArray();
		 for(char i : str)
		 {
			 if(t.getChild(i)!=null)
				 t = t.getChild(i);
			 else
				 flag = 1;
		 }

		 if(flag == 1) return temp;

		 Queue<TrieNode> q = new LinkedList<TrieNode>();
		 q.add(t);
		 while(q.size()!=0 && numCompletions > 0)
		 {
			 TrieNode z = q.remove();
			 if(z.endsWord())
			 {
				 temp.add(z.getText());
				 numCompletions--;
			 }
			 for(char s : z.getValidNextCharacters())
				 q.add(z.getChild(s));
		 }

		 return temp;

		 }


	// For debugging
	public void printTree() {
		printNode(root);
	}

	/** Do a pre-order traversal from this node down */
	public void printNode(TrieNode curr) {
		if (curr == null)
			return;

		System.out.println(curr.getText());

		TrieNode next = null;
		for (Character c : curr.getValidNextCharacters()) {
			next = curr.getChild(c);
			printNode(next);
		}
	}

	private void getChildren(TrieNode current)
	{
		if(current != null)
		{
			if(current.endsWord())
			{
				size++;
			}


			Set<Character> chars = current.getValidNextCharacters();
			for(char c: chars)
			{
				getChildren(current);
			}
		}
	}

}