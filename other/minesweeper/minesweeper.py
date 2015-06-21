from tkinter import *
import tkinter.messagebox
from random import *
from functools import partial

class Tile():
	def __init__(self, tileObj, mine, mineCount, turned, flagged):
		self.tileObj = tileObj
		self.mine = mine
		self.mineCount = mineCount
		self.turned = turned
		self.flagged = flagged

class Minesweeper():
	def __init__(self, master, width=10, height=10):

		self.master = master
		self.width=width
		self.height=height

		self.frame = Frame(master)
		self.frame.pack()

		self.resetImage = PhotoImage(file="img/normalSmiley.png")
		self.resetButton = Button(self.frame, image=self.resetImage, command=self.reset)
		self.resetButton.grid(row=0, columnspan=width)

		self.flagTileImage = PhotoImage(file="img/flag-tile.png")

		self.flagging = False
		self.flagImage=PhotoImage(file="img/flag.png")
		self.flagButton = Button(self.frame, image=self.flagImage, command=self.flag)
		self.flagButton.grid(row=0, columnspan=1)

		self.mineImage=PhotoImage(file="img/mine.png")
		self.tileImage=PhotoImage(file="img/tile.png")
		self.numbersImg = {0:PhotoImage(file="img/opened.png"),1:PhotoImage(file="img/1.png"), 2:PhotoImage(file="img/2.png"),3:PhotoImage(file="img/3.png"),4:PhotoImage(file="img/4.png"),5:PhotoImage(file="img/5.png"),6:PhotoImage(file="img/6.png"),7:PhotoImage(file="img/7.png"),8:PhotoImage(file="img/8.png")}

		self.buttons = {}

		#################
		## Plain Board ##
		#################
		for x in range(self.width):
			for y in range(self.height):
				key = (x,y)
				actionWithTile = partial(self.onClick, key)			## God damn partial
				## Each tile composes of:
				## 0. button object
				## 1. whether or not tile is a mine
				## 2. how many mines surround it
				## 3. whether or not it is already revealed
				mine = random() < 0.15
				turned = False
				flagged = False
				self.buttons[key] = Tile(Button(self.frame, image=self.tileImage, command=actionWithTile), mine, 0, turned, flagged)
				self.buttons[key].tileObj.grid(row=y+1, column=x)
		for key in list(self.buttons.keys()):
			self.countMines(key)


	#############
	## METHODS ##
	#############

	def reset(self):
		"""
		Function to:
		1. Destroy the current game
		2. Re-init the game
		"""
		self.frame.destroy()
		self.__init__(self.master, self.width, self.height)

	def flag(self):
		if self.flagging:
			self.flagging = False
			self.flagButton.configure(relief=RAISED)
		else:
			self.flagging = True
			self.flagButton.configure(relief=SUNKEN)

	def onClick(self, key):
		""" 
		Function to:
		 1. Reveal tile if it's not a mine
		 2. Lose the game if it is
		"""
		if self.flagging:
			if self.buttons[key].flagged:
				self.buttons[key].tileObj.configure(image=self.tileImage)
				self.buttons[key].flagged = False
			else:
				self.buttons[key].tileObj.configure(image=self.flagTileImage)
				self.buttons[key].flagged = True
		else:
			if not self.buttons[key].mine:		## If button isn't a mine then reveal tile
				self.revealTile(key)
			else:
				self.loseGame()


	def loseGame(self):
		"""
		Function to:
		1. Replace smiley face
		2. Show lost message
		3. Reveal tiles
		"""
		self.lostImage = PhotoImage(file="img/lostSmiley.png")
		self.resetButton.configure(image=self.lostImage)
		for key in self.buttons:			## Reveal buttons
			if not self.buttons[key].turned:
				if not self.buttons[key].mine:			## If button isn't a mine then count mines around it and reveal the tile
					mineCount = self.buttons[(key)].mineCount
					self.buttons[key].tileObj.destroy()
					self.buttons[key].tileObj = Label(self.frame, image=self.numbersImg[mineCount])
				else:
					self.buttons[key].tileObj.destroy()
					self.buttons[key].tileObj = Label(self.frame, image=self.mineImage)
				self.buttons[key].tileObj.grid(row=key[1]+1, column=key[0])
		tkinter.messagebox.showinfo("Minesweeper", "You lost!")


	def countMines(self, key):
		"""
		Function to:
		1. Count how many mines surround a key
		2. Pick an image accordingly and return it
		"""
		x_co, y_co = key[0], key[1]
		xRange, yRange = range(-1,2), range(-1,2)

		## Top row
		if y_co == 0:
			yRange = range(0,2)
		## Bottom row
		elif y_co == self.height-1:
			yRange = range(-1,1)
		## Left column
		if x_co == 0:
			xRange = range(0,2)
		## Right column
		elif x_co == self.width-1:
			xRange = range(-1,1)

		for xShift in xRange:
			for yShift in yRange:
				if self.buttons[(x_co+xShift,y_co+yShift)].mine:
					self.buttons[key].mineCount += 1


	def revealTile(self, key):
		"""
		Function to:
		1. Reveal a tile based on whether or not it's a mine and how many mines are around it
		"""
		if not self.buttons[key].mine:			## If button isn't a mine then count mines around it and reveal the tile
			mineCount = self.buttons[key].mineCount
			if mineCount == 0:
				self.zeroMines(key)
			self.buttons[key].tileObj.destroy()
			self.buttons[key].tileObj = Label(self.frame, image=self.numbersImg[mineCount])
		else:
			self.buttons[key].tileObj.destroy()
			self.buttons[key].tileObj = Label(self.frame, image=self.mineImage)
		self.buttons[key].tileObj.grid(row=key[1]+1, column=key[0])
		self.buttons[key].turned = True


	def zeroMines(self, key):
		"""
		Function to:
		1. Check if there are 0-mine tiles around, if there are, reveal them, and keep exploring
		2. If next tile isn't a 0-mine tile, reveal it, stop
		Problem:
		1. No base case, right now if I add a recursive call, it basically just keeps going side to side or up and down forever
		"""
		x_co, y_co = key[0], key[1]
		xRange, yRange = range(-1, 2), range(-1, 2)
		## Top row
		if y_co == 0:
			yRange = range(0,2)
		## Bottom row
		elif y_co == self.height-1:
			yRange = range(-1,1)
		## Left column
		if x_co == 0:
			xRange = range(0,2)
		## Right column
		elif x_co == self.width-1:
			xRange = range(-1,1)

		for xShift in xRange:
			for yShift in yRange:
				newKey = (x_co+xShift, y_co+yShift)
				if self.isBlank(newKey):
					self.buttons[newKey].tileObj.destroy()
					self.buttons[newKey].tileObj = Label(self.frame, image=self.numbersImg[0])
					self.buttons[newKey].tileObj.grid(row=y_co+yShift+1, column=x_co+xShift)
					self.buttons[newKey].turned = True
				else:
					self.revealTile((x_co+xShift, y_co+yShift))


	def isBlank(self, key):
		return self.buttons[key].mineCount == 0


def main():
	global root
	root = Tk()
	root.title("Minesweeper")
	minesweeper = Minesweeper(root, 16, 16)
	root.mainloop()
if __name__ == "__main__":
	main()