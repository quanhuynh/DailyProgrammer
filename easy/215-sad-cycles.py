"""
[2015-05-18] Challenge #215 [Easy] Sad Cycles
https://www.reddit.com/r/dailyprogrammer/comments/36cyxf/20150518_challenge_215_easy_sad_cycles/
Given a base b and a starting number n, produce the first x terms in the b-sad cycle for n.
The next number in a sad cycle is defined as taking the current number, and add up the b-powers of each digit.
A sequence is produced when the process is repeated over and over again

For example, the 2-sad cycle starting at 20 is: 
2^2 + 0^2 = 4
4^2 = 16
1^2 + 6^2 = 1 + 36 = 37
...
"""

def power_digits(b, n):
	#Use recursion to b-power each digit of n separately to obtain a result
	#The result would be (n % 10)**b + power_digits(b, n//10) and so on
	if n < 10:
		return n**b
	else:
		return (n % 10) ** b + power_digits(b, n//10)

def return_cycle(x, b, n):
	#Return the first x terms of a b-sad cycle starting at n
	if x == 0:
		return ""
	else:
		return "{} ".format(n) + return_cycle(x-1, b, power_digits(b, n))
