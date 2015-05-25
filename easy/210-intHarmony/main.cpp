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
int compare(string x, string y)
//compare how similar two binary strings are
//this function goes through each element in a string and if it is similar, it is a match
{
    double match = 0;
    for (int i=0; i < x.length(); ++i)
    {
        if ( x[i] == y[i] )
        {
            match += 1;
        }
    }
    double percentage = (match / 32.0) * 100;
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
