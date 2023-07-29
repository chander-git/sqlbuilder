package com.bitcomm;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class AppTest 
{
	
	
    
	public String versionInformation() {
	    return readGitProperties();
	}

	private String readGitProperties() {
	    ClassLoader classLoader = getClass().getClassLoader();
	    InputStream inputStream = classLoader.getResourceAsStream("git.properties");
	    try {
	        return readFromInputStream(inputStream);
	    } catch (IOException e) {
	        e.printStackTrace();
	        return "Version information could not be retrieved";
	    }
	}

	private String readFromInputStream(InputStream inputStream)
	throws IOException {
	    StringBuilder resultStringBuilder = new StringBuilder();
	    try (BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
	        String line;
	        while ((line = br.readLine()) != null) {
	            resultStringBuilder.append(line).append("\n");
	        }
	    }
	    return resultStringBuilder.toString();
	}
	
	public void shouldAnswerWithTrue() throws IOException
	{
		
	

		
//		ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
//		Resource[] resources = resolver.getResources("classpath*:test/*.json");
//
//		    for(Resource r: resources) {
//		        InputStream inputStream = r.getInputStream();
//		        File somethingFile = File.createTempFile(r.getFilename(), ".cxl");
//		        try {
//		            FileUtils.checkCanReadAndIsFile( somethingFile);
//		        } finally {
//		            IOUtils.closeQuietly(inputStream);
//		        }
////		        LicenseManager.setLicenseFile(somethingFile.getAbsolutePath());
//		        System.out.println(("File Path is " + somethingFile.getAbsolutePath()));
//		    }
	
//		    InputStream in1 = this.getClass().getClassLoader()
//                    .getResourceAsStream("git.properties");
////From Class, the path is relative to the package of the class unless
////you include a leading slash, so if you don't want to use the current
////package, include a slash like this:
//InputStream in = this.getClass().getResourceAsStream("/git.properties");
////		read();
	
		
//		System.out.println(versionInformation());
		List<Person> persons = new ArrayList<>(Arrays.asList(
				new Person("Bs", 15),
				new Person("Css", 25),
				new Person("Addd", 20),
				new Person("Kddddd", 203),
				new Person("N", 10)
				));

		Comparator<Person> d = new Comparator<Person>(){

			@Override
			public int compare(Person p1, Person p2) {
				//asc
				return Long.compare(p1.getAge() , p2.getAge());
			}
			
		};
		persons.sort(d.reversed());
		
//		Collections.sort(persons, new Comparator<Person>() {
//			@Override
//			public int compare(Person p1, Person p2) {
//				return Long.compare(p2.getAge() , p1.getAge());
//			}
//		});
		try {
			InputStream in = getClass().getResourceAsStream("/git.properties");
			System.out.println("p[ath::"+getClass().getResource("/git.properties").getPath());
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			while (true) {
				
				System.out.println(reader.readLine());
				if(reader.readLine()==null)
					break;
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(-1);
		} 

		System.out.println(persons);

	}

//	private void read() {
//		   String fileName = "demo.txt";
//	         
//	        BufferedReader br = null;
//			try {
//				br = new BufferedReader(new FileReader(fileName));
//			} catch (FileNotFoundException e1) {
//				// TODO Auto-generated catch block
//				e1.printStackTrace();
//			}
//	         
//	        try {
//	            String line;
//	            try {
//					while ((line = br.readLine()) != null) {
//					   System.out.println(line);    
//					}
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//	        } finally {
//	            try {
//					br.close();
//				} catch (IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//	        }
//		System.exit(0);		
//	}

//	private Map<String, Object> processurecall() {
//
//		   JdbcTemplate jdbcTemplate= new JdbcTemplate(DataBaseUtill.getInstance().getDataSouce());
//		   SqlParameter vDeviceId = new SqlParameter("@vDeviceId",9,"");
//			 SqlParameter vPage = new SqlParameter("@vPage",11,"");
//			 SqlParameter vPages = new SqlParameter("@vPages",11,2);
//			  List<SqlParameter> parameters = Arrays.asList(vDeviceId,vPage,vPages);
//			    
//			    return jdbcTemplate.call(new CallableStatementCreator() {
//
//					@Override
//					public java.sql.CallableStatement createCallableStatement(java.sql.Connection con)
//							throws java.sql.SQLException {
//						
//						  java.sql.CallableStatement cs = con.prepareCall("{call GetHistoryDataByDeviceID(?,?,?)}");
//					        cs.setString(1, "");
//					        return cs;
//					}
//			 
//			
//			    }, parameters);
//		
//		
//	}



}

class Person
{
	private String name;
	private int age;

	public Person(String name, int age)
	{
		this.name = name;
		this.age = age;
	}

	@Override
	public String toString()
	{
		return "{" +
				"name='" + name + '\'' +
				", age=" + age +
				'}';
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}
}

class Main
{
	public static void main(String[] args)
	{

	}
}