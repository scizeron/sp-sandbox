The application exposes an endpoint /sleep and could take a 'd' request parameter : delay (in milliseconds)
When the application receives a sleep request, it sleeps during the specified time
If the sleepy time exceeds a configuration threshold (2 000 ms), the response is KO, status 500.

Each request is monitored and 2  metrics are stored : the requests count (total) and the errors count.
Firstly, these metrics are availables (via /admin/metrics)
Secondly, they participate to determine the application health indicator value.

Too much errors and the application health indicator could be OUT_OF_SERVICE or DOWN (vs UP when  the failure ratio < 50 %)

With a random load, instances could be marked as OUT_OF_SERVICE or DOWN

If a LB checks the health status, it can detect the heath indicator bad status and stop to forward requests 
during a specified time or until the heath indicator becomes green again

Each 2 minutes, if the status is DOWN, the counts are reset, the LB will detect the new UP status 
and will restart to forward the incoming requests.

Usefull to test AWS autoscaling 