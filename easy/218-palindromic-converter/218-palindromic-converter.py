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