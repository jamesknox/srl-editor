/* 
 * Copyright (c) 2008, National Institute of Informatics
 *
 * This file is part of SRL, and is free
 * software, licenced under the GNU Library General Public License,
 * Version 2, June 1991.
 *
 * A copy of this licence is included in the distribution in the file
 * licence.html, and is also available at http://www.fsf.org/licensing/licenses/info/GPLv2.html.
 */
package srl.wordlist;

import java.util.*;
import java.io.*;
import java.util.regex.*;
import mccrae.tools.strings.Strings;
import mccrae.tools.struct.*;
import org.apache.lucene.analysis.*;
import srl.corpus.Processor;

/**
 * A set of word lists. The individual values are stored in as 
 * WordListEntry objects. All wordlists are indexed globally and may
 * be accessed through functions on this list (this is so that a wordlist
 * may be found by its identifier alone without knowing its set)
 * @author john
 */
public class WordListSet {
    /** This represents the wordlists. It is a map indexed by the wordlist names */
    public ListenableMap<String,ListenableSet<WordListEntry>> wordLists;
    /** The name of this word list set */
    public final String name;
    /** The linguistic processor */
    private final Processor processor;
    /** A comment for each wordlist. Indexed by wordlist name */
    public Map<String,String> comment = new HashMap<String,String>();
    
    /** Create a new wordlist set
     * @param name The wordlist set name
     * @param processor The linguistic processor 
     */
    public WordListSet(String name, Processor processor) {
        this.name = name;
        this.processor = processor;
        wordLists = new ListenableMap<String,ListenableSet<WordListEntry>>(new HashMap<String,ListenableSet<WordListEntry>>());
        allWordListSets.put(name, this);
    }
    
    /** Load a word list set from a file 
     * @param file The file
     * @param processor The linguistic processor used
     */
    public static WordListSet loadFromFile(File file, Processor processor) throws IOException {
        System.out.println("Loading: " + file);
        String wlName = file.getName();
        if(wlName.matches(".*\\.wordlist\\.srl")) {
            wlName = wlName.substring(0,wlName.length()-13);
        }
        WordListSet wl = new WordListSet(wlName, processor);
        String cmt = "";
        BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
        for(String in = br.readLine(); in != null; in = br.readLine()) {
            String[] ss = in.split("#");
            in = ss[0];
            if(ss.length > 1 && ss[1].length() > 0) {
                ss[1] = ss[1].replaceAll("^\\s+", "");
                cmt = cmt + ss[1] + "\n";
            } 
            if(in.matches("\\s*"))
                continue;
            Matcher m = Pattern.compile("\\s*@(\\w+)\\((.*)\\)\\s*").matcher(in);
            if(!m.matches())
                throw new RuntimeException("Syntax Error at " + in);
            String listName = m.group(1);
            if(m.matches()) {
                ListenableSet<WordListEntry> set = new ListenableSet<WordListEntry>(new TreeSet<WordListEntry>());
                char[] list = m.group(2).toCharArray();
                StringBuffer name = new StringBuffer();
                boolean inLiteral = false;
                for(int i = 0; i < list.length; i++) {
                    if(inLiteral) {
                        if(list[i] == '\\') {
                            name.append(list[i]);
                            name.append(list[++i]);
                        } else if(list[i] == '\"') {
                            set.add(wl.getEntry(name.toString()));
                            name = new StringBuffer();
                            inLiteral = false;
                        } else {
                            name.append(list[i]);
                        }
                    } else {
                        if(list[i] == '\"') {
                            inLiteral = true;
                        }
                    }
                }
                
                wl.wordLists.put(listName, set);
                wl.comment.put(listName, cmt);
                cmt = "";
            } else {
                System.err.println(in);
            }
        }
        for(String s : wl.wordLists.keySet()) {
            if(allWordLists.keySet().contains(s))
                throw new IllegalArgumentException("Wordlist @" + s + " already exists");
        }
        allWordLists.putAll(wl.wordLists);
        for(String s : wl.wordLists.keySet()) {
            allWordSets.put(s, wl);
        }
        return wl;
    }
   
    /** Write the word list set to disk
     * @param file The file to write to
     */
    public void write(File file) throws IOException {
        PrintStream ps = new PrintStream(file,"UTF-8");
        for(Map.Entry<String,ListenableSet<WordListEntry>> entry : wordLists.entrySet()) {
            String cmt;
            if(comment.get(entry.getKey()) != null && 
                    comment.get(entry.getKey()).length() > 0) {
                cmt = "# " + comment.get(entry.getKey());
                cmt = cmt.replaceAll("(\n|\r)(?=.)", "\n# ");
                if(cmt.charAt(cmt.length() - 1) != '\n') {
                    cmt = cmt + "\n";
                }
            } else {
                cmt = "";
            }
            ps.println(cmt + "@" + entry.getKey() + "(\"" +
                Strings.join("\",\"", entry.getValue()) + "\")");
        }
        ps.close();
    }
    
    /**
     * Add a new list to this set of word lists
     * @param name The identifier for this name
     * @return True if the list was successfully added
     */
    public boolean addList(String name) {
        if(allWordLists.get(name) != null) 
            return false;
        ListenableSet<WordListEntry> set = new ListenableSet<WordListEntry>(new TreeSet<WordListEntry>());
        wordLists.put(name, set);
        allWordLists.put(name, set);
        allWordSets.put(name, this);
        return true;
    }
    
    static Map<String,ListenableSet<WordListEntry>> allWordLists = new HashMap<String,ListenableSet<WordListEntry>>();
    static Map<String,WordListSet> allWordSets = new HashMap<String,WordListSet>();
    static Map<String,WordListSet> allWordListSets = new HashMap<String,WordListSet>();
    
    /** Get a specific word list by name */
    public static ListenableSet<WordListEntry> getWordList(String wordListName) {
        return allWordLists.get(wordListName);
    }
    
    /** Get all word list identifiers */
    public static Set<String> getAllWordListNames() {
        return allWordLists.keySet();
    }
    
    /** Get a word list set by the name of one of the lists it contains */
    public static WordListSet getWordListSetByList(String wordListName) {
        return allWordSets.get(wordListName);
    }
    
    public static WordListSet getWordListSetByName(String wordListName) {
        return allWordListSets.get(wordListName);
    }
    
    /**
     * Add a list of terms to a wordlist
     * @param wordListName The word list ID
     * @param entries The new entries
     */
    public static void addToList(String wordListName, Collection<String> entries) {
        Set<WordListEntry> wordList = getWordList(wordListName);
        WordListSet wl = getWordListSetByList(wordListName);
        for(String entry : entries) {
            wordList.add(wl.getEntry(entry));
        }
    }
    
    /**
     * Add a list of terms to a wordlist
     * @param wordListName The word list ID
     * @param entries The new entries
     */
    public static void addToList(String wordListName, String[] entries) {
        Set<WordListEntry> wordList = getWordList(wordListName);
        WordListSet wl = getWordListSetByList(wordListName);
        for(String entry : entries) {
            wordList.add(wl.getEntry(entry));
        }
    }
    
    /** (EXPERT) For a word list find all terms in it which token. This matches
     * not only those that exactly match but also those that may match
     * as more tokens are read.
     */
    public static SortedSet<WordListEntry> getMatchSet(String name, String token) {
        SortedSet<WordListEntry> set = ((SortedSet<WordListEntry>)allWordLists.get(name).getSet());
        WordListSet wl = getWordListSetByList(name);
        LinkedList<String> l1 = new LinkedList<String>();
        LinkedList<String> l2 = new LinkedList<String>();
        l1.add(token);
        l2.add(token);
        l2.add("\uffff");
        return set.subSet(wl.getEntry(l1), wl.getEntry(l2));
    }
    
    /** Clear the list */
    public static void reset() {
        allWordLists = new HashMap<String,ListenableSet<WordListEntry>>();
        allWordSets = new HashMap<String,WordListSet>();
    }
    
    /**
 * @author John McCrae, National Institute of Informatics
 */
/** Create a new Entry object attached to this word list set */
    public WordListEntry getEntry(String s) {
        return new WordListEntry(s, processor);
    }
    
    /** Create a new Entry object attached to this word list set 
     * @param The (tokenized) word list entry
     */
    private WordListEntry getEntry(List<String> s) {
        return new WordListEntry(s);
    }
}
