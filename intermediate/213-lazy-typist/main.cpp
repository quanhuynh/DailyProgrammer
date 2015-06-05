/*
[2015-05-06] Challenge #213 [Intermediate] The Lazy Typist
https://www.reddit.com/r/dailyprogrammer/comments/351b0o/20150506_challenge_213_intermediate_the_lazy/
Describe movements of the hunt & peck typing style given a string of input
For example, for "Hello":
Shift: Left hand on Shift
H: Right hand on h
e: Move left hand to e (effort: 4)
l: Move right hand to l (effort: 3)
l: Move right hand to l (effort: 0)
o: Move right hand to o (effort: 2)
*/
#include <iostream>
#include <string>
#include <cmath>

using namespace std;

string keyboard =
    "qwertyuiop"
    "asdfghjkl;"
    "^zxcvbnm,^"
    "   #####  ";

int spaceKeys[5] = {33, 34, 35, 36, 37};
int leftShift = 20, rightShift = 29;

int effortInt(int index1, int index2)
{
    int row1, row2, col1, col2;
    row1 = index1 / 10, col1 = index1 % 10;
    row2 = index2 / 10, col2 = index2 % 10;
    return abs(row1 - row2) + abs(col1 - col2);
}

int closestIndex(char fromKey, char toKey)
{
    int fromPos = keyboard.find(fromKey);

    if (toKey == '^') {
        if (effortInt(fromPos, leftShift) < effortInt(fromPos, rightShift)) {
            return leftShift;
        }
        else {
            return rightShift;
        };
    }
    else if (toKey == ' ') {
        int minSpaceIndex, lowestEffort = 40;
        for (int i=0; i<5; i++) {
            if (effortInt(fromPos, spaceKeys[i]) < lowestEffort ) {
                lowestEffort = effortInt(fromPos, spaceKeys[i]);
                minSpaceIndex = spaceKeys[i];
            };
        };
        return minSpaceIndex;
    };
}

int main()
{
    string line;
    cout << "Enter a line to type: ";
    getline(cin, line);

    int curIndex;
    int leftKey, rightKey;
    int totalEffort;

    //starting positions
    if (keyboard.find(tolower(line[0])) % 10 <= 4) {
        leftKey = keyboard.find(tolower(line[0]));
        rightKey = rightShift;
        cout << "Right hand on Shift" << endl;
        cout << "Left hand on " << static_cast<char>(tolower(line[0])) << endl;
    }
    else {
        leftKey = leftShift;
        rightKey = keyboard.find(tolower(line[0]));
        cout << "Left hand on Shift" << endl;
        cout << "Right hand on " << static_cast<char>(tolower(line[0])) << endl;
    };

    curIndex = 1;
    totalEffort = 0;

    while (curIndex < line.length()) {
        char curChar = line[curIndex];
        int keyboardIndex = keyboard.find(curChar);
        if (islower(curChar)) {
            if (effortInt(leftKey, keyboardIndex) < effortInt(rightKey, keyboardIndex)) {
                cout << "Move left hand to " << curChar << " (effort " << effortInt(leftKey, keyboardIndex) << ")" << endl;
                totalEffort += effortInt(leftKey, keyboardIndex);
                leftKey = keyboardIndex;
            }
            else {
                cout << "Move right hand to " << curChar << " (effort " << effortInt(rightKey, keyboardIndex) << ")" << endl;
                totalEffort += effortInt(rightKey, keyboardIndex);
                rightKey = keyboardIndex;
            }
        };
        if (curChar == ' ') {
            int effortFromLeft = effortInt(leftKey, closestIndex(leftKey, ' ')), effortFromRight = effortInt(rightKey, closestIndex(rightKey, ' '));
            if (effortFromLeft < effortFromRight) {
                cout << "Move left hand to Space " << "(effort " << effortFromLeft << ")" << endl;
                totalEffort += effortFromLeft;
                leftKey = closestIndex(leftKey, ' ');
            }
            else {
                cout << "Move right hand to Space " << "(effort " << effortFromRight << ")" << endl;
                totalEffort += effortFromRight;
                rightKey = closestIndex(rightKey, ' ');
            }
        };
        if (isupper(curChar)) {
            int effortFromLeft = effortInt(leftKey, closestIndex(leftKey, '^')), effortFromRight = effortInt(rightKey, closestIndex(rightKey, '^'));
            if (effortFromLeft < effortFromRight) {
                cout << "Move left hand to Shift " << "(effort " << effortFromLeft << ")" << endl;
                totalEffort += effortFromLeft;
                leftKey = closestIndex(leftKey, '^');
                cout << "Move right hand to " << static_cast<char>(tolower(curChar)) << " (effort " << effortInt(rightKey, keyboardIndex) << ")" << endl;
                totalEffort += effortInt(rightKey, keyboardIndex);
                rightKey = keyboardIndex;
            }
            else {
                cout << "Move right hand to Shift " << "(effort " << effortFromRight << ")" << endl;
                totalEffort += effortFromRight;
                rightKey = closestIndex(rightKey, '^');
                cout << "Move left hand to " << static_cast<char>(tolower(curChar)) << " (effort " << effortInt(leftKey, keyboardIndex) << ")" << endl;
                totalEffort += effortInt(leftKey, keyboardIndex);
                leftKey = keyboardIndex;
            };
        };
        curIndex += 1;
    };
    cout << "Total effort: " << totalEffort << endl;

    return 0;
}
