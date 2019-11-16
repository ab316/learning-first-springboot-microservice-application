# Springboot Microservice Application
Simple micro-services application developed while following a tutorial on Medium. The following features are show-cased in this application:
* Eurkea server for service discovery and load-balancing
* Zuul server as Gateway for clients to access the application
* User/name password for JWT-based authentication
* Hystrix fallback

The application consists of the following micro-services
* Image Service
* Gallery Service
* Eureka Server
* Zuul Server
* Auth Service

## Usage
### Get an Authentication Token
Use one of the following request to get an authentication token
#### Standard User
~~~
POST http://localhost:9100/auth
{
	"username": "abdullah",
	"password": "12345"
}
~~~

#### Admin User
~~~
POST http://localhost:9100/auth
{
	"username": "admin",
	"password": "12345"
}
~~~

The token will be received in the response body

### Make Request to Gallery
Make the following request with the given header

`GET http://localhost:8762/gallery/gallery/5`

`Authorization: <YOUR TOKEN>`

## Other Features
Try taking down the image and service, wait for 30 seconds and making the above request to
Gallery service again. This time, the gallery service will return a fallback response.
