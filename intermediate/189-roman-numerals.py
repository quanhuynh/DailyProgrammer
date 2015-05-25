"""
[2014-11-19] Challenge #189 [Intermediate] Roman Numeral Conversion
https://www.reddit.com/r/dailyprogrammer/comments/2ms946/20141119_challenge_189_intermediate_roman_numeral/
Given a Roman numeral or a base-10 number, convert to the other
convert_roman can convert Roman numbers up to 4999
"""
from itertools import groupby
romans = {'I':1, 'V':5, 'X':10, 'L':50, 'C':100, 'D':500, 'M':1000}

def valid(roman):
	"""Check if a Roman numeral is valid"""
	all_chars = [[k, len(list(g))] for k, g in groupby(roman)]
	for char in all_chars:
		if char[0] != 'M' and char[1] > 3:
			return False
	return True


def subtractable(num1, num2):
	"""Check if a Roman character can be subtracted from the next character"""
	if num1 == 'I':
		return num2 == 'V' or num2 == 'X'
	if num1 == 'X':
		return num2 == 'L' or num2 == 'C'
	if num1 == 'C':
		return num2 == 'D' or num2 == 'M'


def convert_roman(roman):
	"""Convert Roman numerals up to 4999"""
	if not valid(roman):
		return "This is not a valid numeral"
	if roman == '':
		return 0
	if len(roman) == 1:
		return romans[roman]
	if subtractable(roman[0], roman[1]):
		return romans[roman[1]] - romans[roman[0]] + convert_roman(roman[2:])
	else:
		return romans[roman[0]] + convert_roman(roman[1:])
