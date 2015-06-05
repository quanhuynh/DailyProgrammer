//[2015-04-27] Challenge #212 [Easy] Rövarspråket
//https://www.reddit.com/r/dailyprogrammer/comments/341c03/20150427_challenge_212_easy_r%C3%B6varspr%C3%A5ket/
//Convert an input string into Rövarspråket. Rules are simple: if a letter is a consonant, double it and put an 'o' in between
//Examples:
//b -> bob
//Testing -> Totesostotinongog (Notice that capitalization is removed in second t)

#include <iostream>
#include <string>
using namespace std;

int main()
{
    string consonants = "BbCcDdFfGgHhJjKkLlMmNnPpQqRrSsTtVvWwXxZz";

    string inputString;
    cout << "Say something: ";
    getline(cin, inputString);

    string outputString;
    for (int i=0; i < inputString.length(); i++) {
        size_t searchResult = consonants.find(inputString[i]);
        if (searchResult != string::npos) {
            outputString += inputString[i];
            outputString += "o";
            outputString += tolower(inputString[i]);
        }
        else {
            outputString += inputString[i];
        }
    }

    cout << outputString << endl;

    return 0;
}
