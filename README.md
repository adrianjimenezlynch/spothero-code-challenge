# Spothero code Challenge

## Run with Jetty:

This will run the application on a local jetty server. 

    mvn jetty:run


To call:

### JSON:

Request:
    
    curl -XGET -H "Accept: application/json" 'http://localhost:8080/rest/rate?startDate=2015-07-01T09:00:00Z&endDate=2015-07-01T12:00:00Z'

Response:

    {
        "rate": 1750
    }
### XML Request:

Request:

    curl -XGET -H "Accept: application/xml" 'http://localhost:8080/rest/rate?startDate=2015-07-01T09:00:00Z&endDate=2015-07-01T12:00:00Z'

Response:

    <?xml version="1.0" encoding="UTF-8" standalone="yes"?>
    <rateResponse>
        <rate>1750</rate>
    </rateResponse>

### Protocol Buffer request:

    curl -XGET -H "Accept: application/protobuf" 'http://localhost:8080/protobuf/rate?startDate=2015-07-01T09:00:00Z&endDate=2015-07-01T12:00:00Z'

## Run with Docker

Build Docker app:
 
    docker build --tag=myapp .
Run Docker app: 

    docker run -p 18080:8080 -t -i myapp
    
** To call services in Docker container use the same curl commands but change the port to 18080** 