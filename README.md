# tqh

## RESTful endpoints:
### POST:
#### /update 
data structure:  
```
[{"user_id": 0, "led_states": [false, true, false, true, ...], "time":7},{"user_id": 1, "led_states": [false, true, false, true, ...], "time":11}]
```
###	GET:
####	/status	
reply structure:
```
[{"user_id": 0, "led_states": [false, true, false, true, ...],"time":7},{"user_id": 1, "led_states": [false, true, false, true, ...], "time":8}]
```
