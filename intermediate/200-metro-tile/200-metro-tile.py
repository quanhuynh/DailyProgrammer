"""
[2015-02-04] Challenge #200 [Intermediate] Metro Tile Meltdown
https://www.reddit.com/r/dailyprogrammer/comments/2uo3yf/20150204_challenge_200_intermediate_metro_tile/
Given a text-file input of tiles (refer to data.txt), analyze the characters, positions, and dimensions
"""
def analyzeTiles(dataFile):
	data = open(dataFile).read().splitlines()[1:]
	width, height = len(data[0]), len(data)
	chars, starts = [], []

	## iterate through file and find starting positions
	for y in range(height - 1):
		for x in range(width - 1):
			curChar = data[y][x]
			if curChar not in chars and curChar != '.':
				chars.append(curChar)
				starts.append((curChar, x, y))

	## find dimensions of tiles
	for char, startX, startY in starts:
		dimX, dimY = 0, 0
		#width
		while data[startY][startX + dimX] != '.':
			dimX += 1
		#height
		while data[startY + dimY][startX] != '.':
			dimY += 1
		print("{0}x{1} tile of character {2} located at {3}".format(dimX, dimY, char, (startX, startY)))