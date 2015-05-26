"""
[7/21/2014] Challenge #172 [Easy] ■■□□□▦■□
https://www.reddit.com/r/dailyprogrammer/comments/2ba3g3/7212014_challenge_172_easy/
Given a string, output a pbm format that displays the string through 0s and 1s (or any characters desired)
*Only supports all-caps text. Lowercase text can be added, but I'm lazy. 
"""
import string

## Initial Set up:
### Reading font.txt
### Turning the font.txt information into a easier-to-read dictionary 
uppercases = string.ascii_uppercase
font_file = open('font.txt', 'r').read()
font_dict = {}
for i in range(len(font_file)):
	if font_file[i] in uppercases: 	#if character is a letter, make it a key
		font_dict[font_file[i]] = []
		for j in range(7):
			font_dict[font_file[i]].append(font_file[(i + 2 + 10*j):(i + 11 + 10 * j)].replace(' ', '').replace('0', ' ').replace('1', '█'))
font_dict[' '] = [' ', ' ', ' ', ' ', ' ', ' ', ' ']		#space between words

## This produces:
## font_dict = {'A': ['00100', '01010', '10001', '11111', '10001', '10001', '10001'],
##							'B': ['11110', '10001', '10001', '11110', '10001', '10001', '11110'],
##							....	}
## with 1's replaced with '█' and 0's with '' for readability


## Printing output
def pbm(string):
	# This function prints a pbm format of the given string
	print("P1")
	print(len(string)*9, 7)
	for i in range(7):
		for char in string:
			print(font_dict[char][i], end=' ')
		print()		#empty space
