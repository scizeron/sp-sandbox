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

***********************************************************
/hello stack
***********************************************************
Daemon Thread [http-nio-auto-2-exec-1] (Suspended (breakpoint at line 29 in SdxController))	
	owns: NioChannel  (id=80)	
	SdxController.hello() line: 29	
	NativeMethodAccessorImpl.invoke0(Method, Object, Object[]) line: not available [native method]	
	NativeMethodAccessorImpl.invoke(Object, Object[]) line: 62	
	DelegatingMethodAccessorImpl.invoke(Object, Object[]) line: 43	
	Method.invoke(Object, Object...) line: 497	
	ServletInvocableHandlerMethod(InvocableHandlerMethod).doInvoke(Object...) line: 221	
	ServletInvocableHandlerMethod(InvocableHandlerMethod).invokeForRequest(NativeWebRequest, ModelAndViewContainer, Object...) line: 137	
	ServletInvocableHandlerMethod.invokeAndHandle(ServletWebRequest, ModelAndViewContainer, Object...) line: 110	
	RequestMappingHandlerAdapter.invokeHandleMethod(HttpServletRequest, HttpServletResponse, HandlerMethod) line: 776	
	RequestMappingHandlerAdapter.handleInternal(HttpServletRequest, HttpServletResponse, HandlerMethod) line: 705	
	RequestMappingHandlerAdapter(AbstractHandlerMethodAdapter).handle(HttpServletRequest, HttpServletResponse, Object) line: 85	
	DispatcherServlet.doDispatch(HttpServletRequest, HttpServletResponse) line: 959	
	DispatcherServlet.doService(HttpServletRequest, HttpServletResponse) line: 893	
	DispatcherServlet(FrameworkServlet).processRequest(HttpServletRequest, HttpServletResponse) line: 966	
	DispatcherServlet(FrameworkServlet).doGet(HttpServletRequest, HttpServletResponse) line: 857	
	DispatcherServlet(HttpServlet).service(HttpServletRequest, HttpServletResponse) line: 622	
	DispatcherServlet(FrameworkServlet).service(HttpServletRequest, HttpServletResponse) line: 842	
	DispatcherServlet(HttpServlet).service(ServletRequest, ServletResponse) line: 729	
	ApplicationFilterChain.internalDoFilter(ServletRequest, ServletResponse) line: 291	
	ApplicationFilterChain.doFilter(ServletRequest, ServletResponse) line: 206	
	WsFilter.doFilter(ServletRequest, ServletResponse, FilterChain) line: 52	
	ApplicationFilterChain.internalDoFilter(ServletRequest, ServletResponse) line: 239	
	ApplicationFilterChain.doFilter(ServletRequest, ServletResponse) line: 206	
	EndpointWebMvcAutoConfiguration$ApplicationContextHeaderFilter.doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain) line: 295	
	EndpointWebMvcAutoConfiguration$ApplicationContextHeaderFilter(OncePerRequestFilter).doFilter(ServletRequest, ServletResponse, FilterChain) line: 107	
	ApplicationFilterChain.internalDoFilter(ServletRequest, ServletResponse) line: 239	
	ApplicationFilterChain.doFilter(ServletRequest, ServletResponse) line: 206	
	WebRequestTraceFilter.doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain) line: 102	
	WebRequestTraceFilter(OncePerRequestFilter).doFilter(ServletRequest, ServletResponse, FilterChain) line: 107	
	ApplicationFilterChain.internalDoFilter(ServletRequest, ServletResponse) line: 239	
	ApplicationFilterChain.doFilter(ServletRequest, ServletResponse) line: 206	
	OrderedHiddenHttpMethodFilter(HiddenHttpMethodFilter).doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain) line: 77	
	OrderedHiddenHttpMethodFilter(OncePerRequestFilter).doFilter(ServletRequest, ServletResponse, FilterChain) line: 107	
	ApplicationFilterChain.internalDoFilter(ServletRequest, ServletResponse) line: 239	
	ApplicationFilterChain.doFilter(ServletRequest, ServletResponse) line: 206	
	OrderedCharacterEncodingFilter(CharacterEncodingFilter).doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain) line: 85	
	OrderedCharacterEncodingFilter(OncePerRequestFilter).doFilter(ServletRequest, ServletResponse, FilterChain) line: 107	
	ApplicationFilterChain.internalDoFilter(ServletRequest, ServletResponse) line: 239	
	ApplicationFilterChain.doFilter(ServletRequest, ServletResponse) line: 206	
	MetricsFilter.doFilterInternal(HttpServletRequest, HttpServletResponse, FilterChain) line: 68	
	MetricsFilter(OncePerRequestFilter).doFilter(ServletRequest, ServletResponse, FilterChain) line: 107	
	ApplicationFilterChain.internalDoFilter(ServletRequest, ServletResponse) line: 239	
	ApplicationFilterChain.doFilter(ServletRequest, ServletResponse) line: 206	
	StandardWrapperValve.invoke(Request, Response) line: 219	
	StandardContextValve.invoke(Request, Response) line: 106	
	NonLoginAuthenticator(AuthenticatorBase).invoke(Request, Response) line: 502	
	StandardHostValve.invoke(Request, Response) line: 142	
	ErrorReportValve.invoke(Request, Response) line: 79	
	StandardEngineValve.invoke(Request, Response) line: 88	
	CoyoteAdapter.service(Request, Response) line: 518	
	Http11NioProcessor(AbstractHttp11Processor<S>).process(SocketWrapper<S>) line: 1091	
	Http11NioProtocol$Http11ConnectionHandler(AbstractProtocol$AbstractConnectionHandler<S,P>).process(SocketWrapper<S>, SocketStatus) line: 668	
	NioEndpoint$SocketProcessor.doRun(SelectionKey, NioEndpoint$KeyAttachment) line: 1521	
	NioEndpoint$SocketProcessor.run() line: 1478	
	ThreadPoolExecutor(ThreadPoolExecutor).runWorker(ThreadPoolExecutor$Worker) line: 1142	
	ThreadPoolExecutor$Worker.run() line: 617	
	TaskThread$WrappingRunnable.run() line: 61	
	TaskThread(Thread).run() line: 745	


