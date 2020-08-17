# ms3CodingChallenge
The purpose of this repo is for the ms3 coding challenge. The Program is to read in a .CSV to sort the complete entries into a SQLite database. The incomplete enteirs will go into a separate .CSV file. 
To run the application just download the repo and run "ms3challenge.java". It is located ms3challenge>src>ms3challenge>ms3challenge.java at the time of writing this document. 

My approch to this assingment was to us Scanner to read in each entry and .split() to split the entry into feilds. If there were no empty fields then the entry was complete and gets put into the SQLite database(gooddata.db). If not then is gets put into a separate csv file(baddata.cvs). When the sorting is done the total stats are written to a log (stats.log).  

the only real change I made from the requirements was to add an id field so I could set the ID to PRIMARY KEY. Other data cannot be guaranteed to not be repeated so it felt like a better way to track entries 
