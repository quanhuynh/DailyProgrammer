/*
[2015-04-13] Challenge #210 [Easy] intHarmony.com
http://www.reddit.com/r/dailyprogrammer/comments/32goj8/20150413_challenge_210_easy_intharmonycom/
Given two integers, return their compatibility percentage using binaries. Also the program returns the opposite binaries of the numbers.
Example:
1 in 4-bit binary is 0001, 2 is 0010. They have 50% compatibility.
1 should avoid 1110, which is 14.
2 should avoid 1101, which is 13.
This program will run on 32-bit integers.
*/

#include <iostream>
#include <bitset>
#include <stdio.h>
#include <string.h>
using namespace std;

string convert_to_binary(int x)
//takes a decimal number and convert to binary string
{
    return bitset<32>(x).to_string();
}

float compare(string x, string y)
//compare how similar two binary strings are
//this function goes through each element in a string and if it is similar, it is a match
{
    float match = 0.0;
    for (int i=0; i < x.length(); ++i)
    {
        if ( x[i] == y[i] )
        {
            match += 1;
        }
    }
    float percentage = (match / 32.0) * 100.0; //since 32 bits
    return percentage;
}

int main()
//takes in two integers, compare their binary compatibility, and return their reversed binaries as well as compatibility percentage
{
    unsigned int x, y;
    cout << "Enter a number: ";
    cin >> x;
    cout << "Enter another number: ";
    cin >> y;

    string xBin = convert_to_binary(x);
    string yBin = convert_to_binary(y);

    cout << "Compatibility: " << compare(xBin, yBin) << "%" << endl;
    cout << x << " should avoid " << ~x << endl;
    cout << y << " should avoid " << ~y << endl;
    return 0;
}
