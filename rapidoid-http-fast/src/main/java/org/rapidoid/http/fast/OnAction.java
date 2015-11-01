package org.rapidoid.http.fast;

/*
 * #%L
 * rapidoid-http-fast
 * %%
 * Copyright (C) 2014 - 2015 Nikolche Mihajlovski and contributors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.util.Map;
import java.util.concurrent.Callable;

import org.rapidoid.annotation.Authors;
import org.rapidoid.annotation.Since;
import org.rapidoid.beany.Beany;
import org.rapidoid.io.Res;

@Authors("Nikolche Mihajlovski")
@Since("4.3.0")
public class OnAction {

	private final ServerSetup chain;

	private final FastHttp[] httpImpls;

	private final String verb;

	private final String path;

	public OnAction(ServerSetup chain, FastHttp[] httpImpls, String verb, String path) {
		this.chain = chain;
		this.httpImpls = httpImpls;
		this.verb = verb;
		this.path = path;
	}

	private void register(byte[] contentType, byte[] response) {
		for (FastHttp http : httpImpls) {
			http.on(verb, path, new FastStaticHttpHandler(http, contentType, response));
		}
	}

	private void register(byte[] contentType, final Object response) {
		register(contentType, new ParamHandler() {
			@Override
			public Object handle(Map<String, Object> params) throws Exception {
				Beany.bind(response, params); // data binding
				return response;
			}
		});
	}

	@SuppressWarnings("unchecked")
	private void register(byte[] contentType, Callable<?> handler) {
		for (FastHttp http : httpImpls) {
			http.on(verb, path, new FastCallableHttpHandler(http, contentType, (Callable<Object>) handler));
		}
	}

	private void register(byte[] contentType, ParamHandler handler) {
		for (FastHttp http : httpImpls) {
			http.on(verb, path, new FastParamsAwareHttpHandler(http, contentType, handler));
		}
	}

	private void register(byte[] contentType, Res resource) {
		for (FastHttp http : httpImpls) {
			http.on(verb, path, new FastResourceHttpHandler(http, contentType, resource));
		}
	}

	/*********************** PLAIN ***********************/

	public ServerSetup plain(String response) {
		plain(response.getBytes());
		return chain;
	}

	public ServerSetup plain(byte[] response) {
		register(FastHttp.CONTENT_TYPE_PLAIN, response);
		return chain;
	}

	public ServerSetup plain(Object response) {
		register(FastHttp.CONTENT_TYPE_PLAIN, response);
		return chain;
	}

	public <T> ServerSetup plain(Callable<T> handler) {
		register(FastHttp.CONTENT_TYPE_PLAIN, handler);
		return chain;
	}

	public <T> ServerSetup plain(ParamHandler handler) {
		register(FastHttp.CONTENT_TYPE_PLAIN, handler);
		return chain;
	}

	public <T> ServerSetup plain(Res resource) {
		register(FastHttp.CONTENT_TYPE_PLAIN, resource);
		return chain;
	}

	/*********************** HTML ***********************/

	public ServerSetup html(String response) {
		html(response.getBytes());
		return chain;
	}

	public ServerSetup html(byte[] response) {
		register(FastHttp.CONTENT_TYPE_HTML, response);
		return chain;
	}

	public ServerSetup html(Object response) {
		register(FastHttp.CONTENT_TYPE_HTML, response);
		return chain;
	}

	public <T> ServerSetup html(Callable<T> handler) {
		register(FastHttp.CONTENT_TYPE_HTML, handler);
		return chain;
	}

	public <T> ServerSetup html(ParamHandler handler) {
		register(FastHttp.CONTENT_TYPE_HTML, handler);
		return chain;
	}

	public <T> ServerSetup html(Res resource) {
		register(FastHttp.CONTENT_TYPE_HTML, resource);
		return chain;
	}

	/*********************** JSON ***********************/

	public ServerSetup json(String response) {
		json(response.getBytes());
		return chain;
	}

	public ServerSetup json(byte[] response) {
		register(FastHttp.CONTENT_TYPE_JSON, response);
		return chain;
	}

	public ServerSetup json(Object response) {
		register(FastHttp.CONTENT_TYPE_JSON, response);
		return chain;
	}

	public <T> ServerSetup json(Callable<T> handler) {
		register(FastHttp.CONTENT_TYPE_JSON, handler);
		return chain;
	}

	public <T> ServerSetup json(ParamHandler handler) {
		register(FastHttp.CONTENT_TYPE_JSON, handler);
		return chain;
	}

	public <T> ServerSetup json(Res resource) {
		register(FastHttp.CONTENT_TYPE_JSON, resource);
		return chain;
	}

	/*********************** BINARY ***********************/

	public ServerSetup binary(String response) {
		binary(response.getBytes());
		return chain;
	}

	public ServerSetup binary(byte[] response) {
		register(FastHttp.CONTENT_TYPE_BINARY, response);
		return chain;
	}

	public ServerSetup binary(Object response) {
		register(FastHttp.CONTENT_TYPE_BINARY, response);
		return chain;
	}

	public <T> ServerSetup binary(Callable<T> handler) {
		register(FastHttp.CONTENT_TYPE_BINARY, handler);
		return chain;
	}

	public <T> ServerSetup binary(ParamHandler handler) {
		register(FastHttp.CONTENT_TYPE_BINARY, handler);
		return chain;
	}

	public <T> ServerSetup binary(Res resource) {
		register(FastHttp.CONTENT_TYPE_BINARY, resource);
		return chain;
	}

}