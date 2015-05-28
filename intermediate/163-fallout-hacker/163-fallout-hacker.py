"""
[5/21/2014] Challenge #163 [Intermediate] Fallout's Hacking Game
https://www.reddit.com/r/dailyprogrammer/comments/263dp1/5212014_challenge_163_intermediate_fallouts/
Simulate the hacking challenge in Fallout 3 (great game).
The computer gives the user a list of words, and the user have four guesses to guess the correct one.
Every time a guess is made, the computer returns:
	1. Whether the guess is correct or not
	2. How many letters are in their correct positions
For example, if the word is MIND, and the user guesses MEND, the computer will return 3/4 correct.
"""

"""
Difficulty scale:
Very easy:		4-5 letters
Easy: 				6-8 letters
Average: 			9-10 letters
Hard: 				11-12 letters
Very Hard: 		13-15 letters
"""
import random 			
#for random selections


def pick_words(n):
## Returns a tuple of a list of random words all of length n and a 'correct' word chosen randomly

	with open('enable1.txt', 'r') as word_file:
		words_list = word_file.read().split('\n')
		n_letter_words = [word for word in words_list if len(word) == n]
		selected_words = []
		for _ in range(random.randint(7, 10)):
			selected_words.append(random.choice(n_letter_words).upper())
		correct_word = random.choice(selected_words)
	return selected_words, correct_word


def guessing(correct_word, guess):
## correct_word and guess are both strings
## Returns x/y format where x is number of letters in correct positions, y is length of correct_word

	correct = 0
	for i in range(len(correct_word)):
		if correct_word[i] == guess[i]:
			correct += 1
	return correct


## Main game

LEVELS = {'1': (4, 5), '2': (6, 8), '3': (9, 10), '4': (11, 12), '5': (13, 15)}
level = input("Difficulty (1-5): ")
n = random.randint(LEVELS[level][0], LEVELS[level][1])
words, correct_word = pick_words(n)
solved = False
tries = 0
for word in words:
	print(word)
while solved is not True and tries != 4:
	guess = input("Make a guess: ")
	if guess not in words:
		print("That's not even in the list. Try again.")
	else:
		result = guessing(correct_word, guess)
		if result == len(correct_word):
			solved = True
			print("Cor-rekt 1337 hax0r!!")
		else:
			tries += 1
			print('{0}/{1} '.format(result, len(correct_word)), "Incorrect guess, {0} tries left".format(4 - tries))
if tries == 4:
	print("You suck.")