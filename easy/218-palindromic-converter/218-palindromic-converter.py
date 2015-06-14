"""
[2015-06-08] Challenge #218 [Easy] Making numbers palindromic
https://www.reddit.com/r/dailyprogrammer/comments/38yy9s/20150608_challenge_218_easy_making_numbers/
Given a number, return the number of steps it takes to convert that number into a palindrome
To make a number closer to a palindrome, add its reversed.
For example, for 68:
68 + 86 = 154 ; 1 step
154 + 451 = 605 ; 2 steps
605 + 506 = 1111 ; 3 steps to a palindrome
"""

def palindrome(nString):
	if nString == '':
		return True
	elif nString[0] != nString[len(nString)-1]:
		return False
	else:
		return palindrome(nString[1:len(nString)-1])

def countSteps(n):
	if palindrome(str(n)):
		return 0
	else:
		return 1 + countSteps(n+int(str(n)[::-1]))

def convert(n):
	steps = countSteps(n)
	palin = n
	for _ in range(steps):
		palin = palin + int(str(palin)[::-1])
	return "{0} gets palindromic after {1} steps to {2}".format(n, steps, palin)