#!/bin/bash
PRODUCT='InputFiles/products.txt'
LISTINGS='InputFiles/listings.txt'
RESULT='Result/'
JAR='Matching-prices/dist/Matching-prices.jar'
java -jar $JAR $PRODUCT $LISTINGS $RESULT



