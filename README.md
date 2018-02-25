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

## Sample server requests:
#### update user info
```
curl -H "Content-Type: application/json" -X POST -d '[{"user_id":0,"led_states":[false, true, false, true, true, true, false, false], "time":9}]' http://localhost:8888/update
```
#### get current user status
```
curl localhost:8888/status
```

## Startup Esteful
Login to raspberry pi:  
```
ssh pi
```
Find out its IP Adress:  
```
hostname -I
```
Start esteful:  
```
cd tqh/Server/build  
sudo ./Server <IP-Adress> <Port>  
```
