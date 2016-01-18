#!/bin/bash
PRODUCT='InputFiles/products.txt'
LISTINGS='InputFiles/listings.txt'
RESULT='Result'
MAINCLASS='Matching-prices/build/classes/matching/prices/MatchingPrices'
java $MAINCLASS $PRODUCTS $LISTINGS $RESULT



