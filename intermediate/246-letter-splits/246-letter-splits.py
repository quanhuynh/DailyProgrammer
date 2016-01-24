"""[2015-12-23] Challenge # 246 [Intermediate] Letter Splits
https://www.reddit.com/r/dailyprogrammer/comments/3xye4g/20151223_challenge_246_intermediate_letter_splits/
Using mapping:
A -> 1
B -> 2
...
Z -> 26
Return how many sequence of letters can be represented by an integer 
"""
mapping = dict([(str(i-64), chr(i)) for i in range(ord("A"), ord("Z")+1)])
def splitNum(number, tail):
	if number == "":
		## Done with parsing number
		print(tail)
		return

	if number[0] in mapping:
		splitNum(number[1:], tail+mapping[number[0]])
	if len(number) > 1 and number[0:2] in mapping:
		splitNum(number[2:], tail+mapping[number[0:2]])

def split(number):
	num = str(number)
	splitNum(num, "")