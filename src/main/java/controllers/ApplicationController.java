/**
 * Copyright (C) 2013 the original author or authors.
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

package controllers;

import ninja.Result;
import ninja.Results;
import javax.persistence.*;
import java.util.List;


import entity.User;
import entity.Books;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import ninja.FilterWith;
import com.google.inject.persist.Transactional;
import ninja.params.PathParam;

@Singleton
@FilterWith(CORSFilter.class)
public class ApplicationController {

	@Inject
	Provider<EntityManager> entitiyManagerProvider;
	

    public Result index() {

        return Results.html();

    }
    
    public Result helloWorldJson() {
        
        SimplePojo simplePojo = new SimplePojo();
        simplePojo.content = "Hello World! Hello Json!";

        return Results.json().render(simplePojo);

    }
    
    public static class SimplePojo {

        public String content;
        
    }
	
	public Result performLogin(User user) {
//    public Result performLogin(@PathParam("id") String id,@PathParam("pass") String pass){
		String userId = user.getId();
		String pwd = user.getPass();
		SimplePojo s=new SimplePojo();
		if (userAuth(userId, pwd)) {
			s.content="Logged In!!";
			//return Result.json().render(s);
		}
		s.content="Error";
		return Results.json().render(s);
    }
	
	private boolean userAuth(String userId, String password) {
		EntityManager entityManager = entitiyManagerProvider.get();
		List<?> users = entityManager.createQuery("from User where id = ?1 and pass = ?2").setParameter(1, userId).setParameter(2, password).getResultList();
		return (users.size() > 0) ? true : false;
	}
	
	public Result getBooks(){
	
		return Results.html();

	}
	
	public Result getAllBooks(){
		EntityManager entityManager = entitiyManagerProvider.get();
		List<Books> users = entityManager.createQuery("from Books").getResultList();
		//System.out.println(users.get(0).getTitle());
//		SimplePojo simplePojo = new SimplePojo();
//        simplePojo.content = users.get(0).getTitle();
//
//        return Results.json().render(simplePojo);
		
		return Results.json().render(users);

	}
	
	public Result finalData(){
		
		return Results.html();
	}
	@Transactional
	public Result updateBook(@PathParam("id") int id,@PathParam("name") String name,@PathParam("author") String author){
		SimplePojo s=new SimplePojo();
		try{
			EntityManager em = entitiyManagerProvider.get();
//			EntityTransaction et = em.getTransaction();
//			et.begin();
			em.createNativeQuery("update Books set name='"+name+"',author= '"+author+"' where id='"+id+"'").executeUpdate();
//			et.commit();
//			em.close();
		s.content="Success";
		}catch(Exception e){
			s.content="Failure";
		}
		return Results.json().render(s);
		
	}
//	public Result addBook(Books b){
	@Transactional
	public Result addBook(@PathParam("id") int id,@PathParam("name") String name,@PathParam("author") String author){
		
		SimplePojo s=new SimplePojo();
		try{
		
		EntityManager em = entitiyManagerProvider.get();
//		EntityTransaction et = em.getTransaction();
//		et.begin();
//		em.createNativeQuery("insert into Books(id,name,author) values("+id+",'"+name+"','"+author+"')").executeUpdate();
//		et.commit();
//		em.close();
		Books b=new Books();
		b.setId(id);
		b.setName(name);
		b.setAuthor(author);
		System.out.println("in Add Book");
		em.persist(b);
		//em.flush();
		//em.close();
		s.content="Success";
		}catch(Exception e){
			//s.content="Failure";
		}
		//s.content="Success";
		return Results.json().render(s);
		
	}
	
	@Transactional
	public Result removeBook(@PathParam("id") int id){
		SimplePojo s=new SimplePojo();
		s.content=id+"";
		try{
			EntityManager em = entitiyManagerProvider.get();
			EntityTransaction et = em.getTransaction();
			//s.content="4";
			//et.begin();
			//s.content="1";
			em.createNativeQuery("delete from Books where id='"+id+"'").executeUpdate();
			//s.content="2";
			//et.commit();
			//s.content="3";
			//em.close();
		//s.content="Success";
		}catch(Exception e){
			//s.content="Failure";
		}
		return Results.json().render(s);
		
	}
	
	
}
