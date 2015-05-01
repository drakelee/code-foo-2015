/**
 * Author: Drake Lee
 * Date: 4/30/15
 * Program takes in an input file containing a crossword and a list of words to find.
 * Given a word, the program will output the crossword with the found word in astericks.  
 * First, the program maps each letter in the crossword to an array holding all its positions.
 * The program uses this map in order to iterate through the combinations of the letters in order 
 * to find a match in the crossword. 
 * The result is outputed with the answer in astericks. 
 */

package com.lee.ign.crossword
import scala.io._
import scala.collection.mutable.{HashMap, HashSet, MutableList}
import java.io.File
import java.io.BufferedInputStream
import java.io.FileInputStream

object Crossword {
  var rows= 1
  var cols= 1
  
  def run(){
    var crossMap : HashMap[Char, Array[Int]] = new HashMap[Char, Array[Int]]
    var wordSet = preparetemp
    var crosswordArray = Array.ofDim[Char](rows*cols)
    
    // Put all chars in crossword in a Map for easier searching. 
    // (key, value) -> (char, position in crossword)
    // Will use the Map when checking for the word in the crossword.
    var reader = new BufferedSource(new BufferedInputStream(new FileInputStream(new File("word-search.txt")))).getLines    
    var temp = reader.next
    var crossword=""
    var nRows = 0
    var nCols = 0
    while(!temp.equals("")){
      crossword+=temp
    	while(temp.indexOf(' ') != -1) {
    		crosswordArray(nCols+nRows*cols) = temp.charAt(0)
    		if(crossMap.get(temp.charAt(0))==None){
    			crossMap.put(temp.charAt(0), new Array[Int](1))
          crossMap.get(temp.charAt(0)).get(0)=nCols+nRows*cols
    		}
    		else{
    			crossMap.put(temp.charAt(0), crossMap.get(temp.charAt(0)).get :+ (nCols+nRows*cols) )
    		}
    		temp=temp.substring(temp.indexOf(' ')+1) 
    	  nCols+=1
    	}   
      crosswordArray(nCols+nRows*cols) = temp.charAt(0)

    	if(crossMap.get(temp.charAt(0))==None){
    		crossMap.put(temp.charAt(0), new Array[Int](nCols+nRows*cols))
    	}
    	else{
    		crossMap.put(temp.charAt(0), crossMap.get(temp.charAt(0)).get :+ (nCols+nRows*cols) )
    	}     
      
      nCols=0
      nRows+=1
      temp = reader.next
      crossword+="\n"
    }
    
    findWord(crossMap, crosswordArray, wordSet)
    
  }
  
  /**
   * Will get the total number of rows and columns in the crossword.
   * Will store all the words that need to be found in the crossword. 
   * @return returns all the available words that can be found in the crossword in a set.
   */
  def preparetemp(): HashSet[String]={
    var reader = new BufferedSource(new BufferedInputStream(new FileInputStream(new File("word-search.txt")))).getLines
    var temp = reader.next
    while(temp.indexOf(' ') != -1) {
      temp=temp.substring(temp.indexOf(' ')+1)
      cols+=1
    }
    rows+=1
    while(!temp.equals("")){
      rows+=1
      temp = reader.next
    }
    
    while(temp.equals("")){
      temp = reader.next
    }
    if(temp.equals("Words to find:")){
      temp = reader.next
      temp = reader.next
    }
    
    var words : HashSet[String] = new HashSet[String]
    while(reader.hasNext){
      words.add(temp.trim())
      temp=reader.next
    }
    words.add(temp.trim())
    words
  }
  
  /**
   * Finds the desired word from a list of words. The answer is printed in astericks in the original crossword.
   * @param crossMap A map that connects a letter to all of its positions in the crossword.
   * @param crossword Array An array that holds the positions of the characters in the crossword in an array.
   * @param wordSet A set of the available words in the crossword. 
   */
  def findWord(crossMap: HashMap[Char, Array[Int]], crosswordArray: Array[Char], wordSet: HashSet[String]){
    printCrossword(crosswordArray)
    println("Words to find:")
    for(word <- wordSet){
      println(word)
    }
    println("Select a word to find in the crossword:")
    for(ln <- io.Source.stdin.getLines){
      if(ln.toUpperCase().equals("QUIT")){
        return
      } else {
        val findWord=ln.toUpperCase()
        if(wordSet.contains(findWord)){
          var beginPos=0 
          var iteratorPos=1
          var found=false // Tells if the word has been found in the crossword.
          var fPotential=false // Tells if a potential letter has been found. 
          var alreadyGuessedSets=new MutableList[MutableList[Int]]// ROund 2 edit
          var answer=new MutableList[Int]
          var iterate=crossMap.get(findWord.charAt(iteratorPos)).get        
          var start=crossMap.get(findWord.charAt(beginPos)).get
          var i=0
          var g=0
          var difference=(-1)
          var illegalSet=false
          var illegalSet2=false
          while(!found){
            i=0
            g=0
            fPotential=false
            if(answer.isEmpty){
              iterate=crossMap.get(findWord.charAt(iteratorPos)).get
              start=crossMap.get(findWord.charAt(beginPos)).get
              while(i!=start.length && !fPotential){
                // Check to see if the potential letter is already in a list of bad sets. 
                val test: MutableList[Int]=answer.clone+=(start(i))
                for(set<-alreadyGuessedSets){
                  if(!test.isEmpty&& !set.isEmpty &&test.equals(set)){
                    illegalSet=true
                  }
                }
                // If the set is not illegal then check to see if the letter is a valid match. 
                if(!illegalSet){
                  while(g!=iterate.length && !fPotential){
                    val test2:MutableList[Int]=answer.clone+=start(i)+=iterate(g)
                    for(set2<-alreadyGuessedSets){
                      if(!test2.isEmpty && !set2.isEmpty && test2.equals(set2)){
                        illegalSet2=true
                      }
                    }
                    if(!illegalSet2){
                      if(Math.abs(start(i)-iterate(g))==1 || Math.abs(start(i)-iterate(g))==cols || 
                          Math.abs(start(i)-iterate(g))==cols+1 || Math.abs(start(i)-iterate(g))==cols-1){ // Check to see if the letter is right next to or above or below or diagonal.
                        answer+=(start(i))
                        if(difference==(-1)) difference=Math.abs(start(i)-iterate(g)) // Needed so consistent differences are kept.
                        beginPos+=1
                        iteratorPos+=1
                        fPotential=true
                        if(beginPos==findWord.length()-1){ // If it reaches this point then the word has been found. 
                          found=true
                          answer+=(iterate(g))
                        }
                      }
                    }
                    else{
                      illegalSet2=false
                    }
                    g+=1
                  } // End of g!=iterate.length
                }
                else{
                  illegalSet=false
                }
                g=0
                i+=1
              } // End of i!=start.length while loop
              if(fPotential){
                fPotential=false
              }
              else{
                beginPos=0
                iteratorPos=1
                difference=(-1)
                alreadyGuessedSets+=answer.clone()
                answer.clear
              }
            } // End of answer.isEmpty statement.
            else {
              start=crossMap.get(findWord.charAt(beginPos)).get
              while((i!=start.length && !fPotential)){
                if(!answer.contains(start(i))){
                  // Check to see if the potential letter is already in a list of bad sets. 
                  val test: MutableList[Int]=answer.clone+=start(i)
                  for(set<-alreadyGuessedSets){
                    if(!test.isEmpty&&test.equals(set)){
                      illegalSet=true
                    }
                  }
                  // If the set is not illegal then check to see if the letter is a valid match. 
                  if(!illegalSet){
                    //for(element<-answer){
                      if(Math.abs(answer.last-start(i))==difference && !fPotential){
                        if(beginPos==findWord.length()-1){ // If it reaches this point then the word has been found.
                          found=true
                          answer+=(start(i))
                        }                        
                        answer+=(start(i))
                        beginPos+=1
                        iteratorPos+=1
                        fPotential=true
                      }
                  }
                  else{
                    illegalSet=false
                  }
                }
                g=0
                i+=1
              } // End of i!=start.length while loop
              if(fPotential){
                fPotential=false
              }
              else{
                beginPos=0
                difference=(-1)
                iteratorPos=1
                alreadyGuessedSets+=answer.clone
                answer.clear
              }              
            }
          }
          //Answer should have been found by here.
          printCrosswordAnswer(crosswordArray, answer)
          println("Word found! Answer in astericks!")
        } else {
          printCrossword(crosswordArray)
          println("Invalid Input.")
        }        
      }
      println("Select a word to find in the crossword or type quit:")
    }  
  }
  /**
   * Method used to print the crossword.
   * @param crosswordArray An array that holds all the characters. 
   */
  def printCrossword(crosswordArray: Array[Char]){
    var temp: Int = 0;
	  for(character <- crosswordArray){
		  if(!(character.toByte.equals(0.toByte))){ // Fixes weird instance where I could not get rid of whitespace. 
			  if(temp%cols==0){
          println()
			  }       
			  print(character + " ")
        temp+=1
		  }      
	  }
    println()
  }

  /**
   * Prints the crossword with the found word. The found word is printed in astericks. 
   * @param crossWordArray An array that holds all the characters of the crossword.
   * @param answers A mutablelist that holds the indices of the answer in the crossword.
   */
  def printCrosswordAnswer(crosswordArray: Array[Char], answers: MutableList[Int]){
    var temp: Int = 0;
    for(character <- crosswordArray){
      if(!(character.toByte.equals(0.toByte))){ // Fixes weird instance where I could not get rid of whitespace. 
        if(temp%cols==0){
          println()
        }
        if(answers.contains(temp)){
          print("* ")
        } else
        print(character + " ")
        temp+=1
      }      
    }
    println()    
  }
}

