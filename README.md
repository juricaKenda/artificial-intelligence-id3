# Artificial intelligence - ID3 / Random forest
ID3 and Random forest algorithm implementations in Java


## Data format
- CSV
- first row contain the names
- following rows contain the data  

Example:  
`weather,temperature,humidity,wind,play`  
`sunny,hot,high,weak,no`  
`sunny,hot,high,strong,no`   
`...`

## Args
arg 1 : train data  
arg 2 : config   
arg 3 : test data  

## Config format
- mode=deprecated 
- model=ID3/RF
- max_depth=maximum depth of the ID3 tree 
- num_trees=for the RF
- feature_ratio=ratio of the total features for each tree in the forest
- example_ratio=ratio of the total inputs for each tree in the forest
