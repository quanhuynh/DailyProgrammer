"""
[2015-08-19] Challenge #228 [Intermediate] Use a Web Service to Find Bitcoin Prices
https://www.reddit.com/r/dailyprogrammer/comments/3hj4o2/20150819_challenge_228_intermediate_use_a_web/
Uses the BitcoinCharts API to fetch information about bitcoin pricings. 
API: http://bitcoincharts.com/about/markets-api/
Endpoint: /v1/trades.csv
Shortnames of bitcoin markets: bitfinex bitstamp btce itbit anxhk hitbtc kraken bitkonan bitbay rock cbx cotr vcx
Shortnames of currency: KRW NMC IDR RON ARS AUD BGN BRL BTC CAD CHF CLP CNY CZK DKK EUR GAU GBP HKD HUF ILS INR JPY LTC MXN NOK NZD PEN PLN RUB SAR SEK SGD SLL THB UAH USD XRP ZAR
"""

from urllib.request import urlopen, URLError
import csv

def getBitcoinPrice(market, currency):
	statement = "http://api.bitcoincharts.com/v1/trades.csv?symbol=" + market + currency;

	try:
		response = urlopen(statement)
		response.read();
	except URLError:
		print("Url doesn't work")