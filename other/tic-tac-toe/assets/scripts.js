// boardArray have the following values:
// null if space is blank
// 0 if space is marked by first player
// 1 if space is marked by second player
var boardArray;
var gameType;
var boardType;
var curPlayer;
var gameEnded;

// Puts board with size nxn into the table w/ id "board"
// Initializes boardArray, a 2D array reprenting the board
function insertBoard(n) {
	var boardTable = document.getElementById("board");
	boardTable.innerHTML = "";
	boardArray = new Array(n);
	for (i=0; i<n; i++) {
		var row = boardTable.insertRow(i);
		boardArray[i] = new Array(n);
		for (j=0; j<n; j++) {
			var cell = row.insertCell(j);
			cell.setAttribute("id", i + "" + j);
			cell.onclick = function() {
				makeMove(this);
			};
			boardArray[i][j] = null;
		}
	}
}

// Starts a new game
// Sets global variables and inserts a new board
function newGame() {
	gameType = document.forms["options"]["game-type"].value;
	boardType = document.forms["options"]["board-type"].value;
	gameEnded = false;
	curPlayer = 0;
	insertBoard(parseInt(boardType));
	var msg = document.getElementById("message");
	msg.innerHTML = "";
	if (gameType=="cvc") {
		while (!gameEnded) {
			makeRandomMove();
		}
	}
	return false;
}

// Makes a human player move when tdElement is clicked
function makeMove(tdElement) {
	var location = tdElement.id;
	var row = parseInt(location.charAt(0));
	var col = parseInt(location.charAt(1));
	if (boardArray[row][col] == null) {
		var img = document.createElement('img');
		boardArray[row][col] = curPlayer;
		if (curPlayer == 0) {
			img.src = "assets/img/x.png";
		} else {
			img.src = "assets/img/o.png";
		}
		tdElement.appendChild(img);
		if (checkWin(row, col) || boardIsFilled()) {
			endGame();
			gameEnded = true;
			return true;
		}
		curPlayer = 1 - curPlayer;
		if (gameType=="pvc" && curPlayer==1) {
			makeRandomMove();
		}
		return true;
	}
	return false;
}

// Make a random move
function makeRandomMove() {
	var boardTableBody = document.getElementById("board").childNodes[0];
	var madeMove = false;
	while (!madeMove) {
		var randomRow = boardTableBody.childNodes[Math.floor(Math.random()*boardTableBody.childNodes.length)];
		var randomTD = randomRow.childNodes[Math.floor(Math.random()*randomRow.childNodes.length)];
		madeMove = makeMove(randomTD);
	}
}

// Check if board is filled
function boardIsFilled() {
	for (i=0; i<parseInt(boardType); i++) {
		for (j=0; j<parseInt(boardType); j++) {
			if (boardArray[i][j] == null) {
				return false;
			}
		}
	}
	curPlayer = -1;
	return true;
}

// Check row, column, and possibly diagonal of this cell for winning condition
function checkWin(row, col) {
	var boardSize = parseInt(boardType);
	var winCond = true;
	//Check Row
	console.log("Checking column");
	for (i=0; i<boardSize; i++) {
		if (boardArray[i][col] != curPlayer) {
			console.log("failed at " + i + col);
			winCond = false;
			break;
		}
	}
	if (winCond) {
		return true;
	}
	winCond = true;
	//Check Column
	console.log("Checking row");
	for (j=0; j<boardSize; j++) {
		if (boardArray[row][j] != curPlayer) {
			console.log("failed at " + row + j);
			winCond = false;
			break;
		}
	}
	if (winCond) {
		return true;
	}
	//Check Diagonal if necessary
	console.log("Checking diagonal");
	if (row == col) {
		winCond = true;
		//left -> right diagonal
		for (k=0; k<boardSize; k++) {
			if (boardArray[k][k] != curPlayer) {
				console.log("failed at " + k + k);
				winCond = false;
				break;
			}
		}
	} else if (row+col == boardSize-1) {
		winCond = true;
		//right -> left diagonal
		for (l=0; l<boardSize; l++) {
			if (boardArray[l][boardSize-1-l] != curPlayer) {
				console.log("failed at " + l + (boardSize-l));
				winCond = false;
				break;
			}
		}
	}
	return winCond;
}

// Disable all td onclicks
// Display message
function endGame() {
	var boardTableBody = document.getElementById("board").childNodes[0];
	for (i=0; i<parseInt(boardType); i++) {
		var row = boardTableBody.childNodes[i];
		for (j=0; j<parseInt(boardType); j++) {
			var cell = row.childNodes[j];
			cell.onclick = null;
		}
	}
	var msg = document.getElementById("message");
	if (curPlayer==0) {
		msg.innerHTML = "PLAYER X WON";
	} else if (curPlayer==1) {
		msg.innerHTML = "PLAYER O WON";
	} else {
		msg.innerHTML = "NO ONE WON";
	}
}