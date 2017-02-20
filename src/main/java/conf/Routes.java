/**
 * Copyright (C) 2012 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package conf;


import ninja.AssetsController;
import ninja.Router;
import ninja.application.ApplicationRoutes;
import controllers.ApplicationController;

public class Routes implements ApplicationRoutes {

    @Override
    public void init(Router router) {  
        
        router.GET().route("/").with(ApplicationController::index);
        router.GET().route("/hello_world.json").with(ApplicationController::helloWorldJson);
		router.POST().route("/login").with(ApplicationController::performLogin);
		router.GET().route("/getBooks").with(ApplicationController.class,"getBooks");
		router.GET().route("/getAllBooks").with(ApplicationController.class,"getAllBooks");
		router.GET().route("/finalData").with(ApplicationController.class,"finalData");
		//router.POST().route("/addBook").with(ApplicationController.class,"addBook");
		//router.POST().route("/updateBook").with(ApplicationController.class,"updateBook");
		//router.POST().route("/removeBook").with(ApplicationController.class,"removeBook");
		router.GET().route("/addBook/{id}/{name}/{author}").with(ApplicationController.class,"addBook");
		router.GET().route("/updateBook/{id}/{name}/{author}").with(ApplicationController.class,"updateBook");
		router.GET().route("/removeBook/{id}").with(ApplicationController.class,"removeBook");
		//router.GET().route("/login/{id}/{pass}").with(ApplicationController::performLogin);
 
        ///////////////////////////////////////////////////////////////////////
        // Assets (pictures / javascript)
        ///////////////////////////////////////////////////////////////////////    
        router.GET().route("/assets/webjars/{fileName: .*}").with(AssetsController::serveWebJars);
        router.GET().route("/assets/{fileName: .*}").with(AssetsController::serveStatic);
        
        ///////////////////////////////////////////////////////////////////////
        // Index / Catchall shows index page
        ///////////////////////////////////////////////////////////////////////
        router.GET().route("/.*").with(ApplicationController::index);
    }

}
