package com.mouadProject.projectManager;

import java.io.File;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class ProjectManagerApplication {


	private static String folder = "C:\\Users\\elgha\\eclipse-workspace\\myProjects\\projectManager\\src\\main\\java\\com\\mouadProject\\projectManager\\";
	private static String typeId = "Long";
	private static String pakage = "com.mouadProject.projectManager";
	
	
	public static void main(String[] args) {
		SpringApplication.run(ProjectManagerApplication.class, args);
		//write();
	}
	
	private static void write() {
		List<File> list = modules(folder);
		list.forEach(f -> {
			String fname = f.getName().replace(".java", "");
				writeDao(folder,f);
				writeService(folder,f);
				writeControllers(folder,f);
			
			
		});
	}
	
	private static List<File> modules(String nfolder){
		nfolder += "domain";
		List<File> list = new ArrayList<File>();
		File folder = new File(nfolder);
        File tab[] = folder.listFiles();
        list = Arrays.asList(tab);
		return list;
	}
	
	private static void writeDao(String nfolder, File model) {
		nfolder += "repositories";
		new File(nfolder).mkdir();
		String fname = model.getName().replace(".java", "");
		try {
			if(!new File(nfolder+"\\"+fname+"Repository.java").exists()) {
				PrintWriter writer = new PrintWriter(nfolder+"\\"+fname+"Repository.java", "UTF-8");
				writer.println("package "+pakage+".repositories;\n");
				writer.println("import "+pakage+".domain."+fname+";");
				writer.println("import org.springframework.data.jpa.repository.JpaRepository;");
				writer.println("import org.springframework.stereotype.Repository;");
				writer.println("\n\n");
				writer.println("@Repository");
				/*BufferedReader br = new BufferedReader(new FileReader(model));
				String line;
				while ((line = br.readLine()) != null) {
					if(line.contains("@Id")) {
						String[] tab = line.split(" ");
						typeId = tab[2];
					}
					  
				}
				br.close();*/
				
				writer.println("public interface "+fname+"Repository extends JpaRepository<"+fname+", "+"Long"+">{");
				writer.println("\t"+fname+" find"+fname+"ById(Long id);");
				writer.println("}");
				writer.close();
			}
		}catch(Exception ex) {
			
		}
	}
	
	private static void writeService(String nfolder, File model) {
		nfolder += "services";
		new File(nfolder).mkdir();
		String fname = model.getName().replace(".java", "");
		try {
			if(!new File(nfolder+"\\"+fname+"Service.java").exists()) {
				PrintWriter writer = new PrintWriter(nfolder+"\\"+fname+"Service.java", "UTF-8");
				writer.println("package "+pakage+".services;\n");
				writer.println("import org.springframework.beans.factory.annotation.Autowired;");
				writer.println("import org.springframework.stereotype.Service;\n");
				writer.println("import "+pakage+".domain."+fname+";");
				writer.println("import "+pakage+".repositories."+fname+"Repository;");
				writer.println("import "+pakage+".services."+fname+"Service;");
				writer.println("import java.util.List;\n");
				writer.println("@Service");
				writer.println("public class "+fname+"Service "+"{\n");

				writer.println("\t@Autowired");
				writer.println("\tprivate "+fname+"Repository repo;");

				writer.println("\t");
				writer.println("\tpublic "+fname+" getOne("+typeId+" id){");
				writer.println("\t\treturn repo.find"+fname+"ById(id);");
				writer.println("\t}");
				writer.println("");
				writer.println("\tpublic List<"+fname+"> findAll(){");
				writer.println("\t\treturn repo.findAll();");
				writer.println("\t}");
				writer.println("\t");
				writer.println("\tpublic "+fname+" save("+fname+" obj){");
				writer.println("\t\treturn repo.save(obj);");
				writer.println("\t}");
				writer.println("\t");
				writer.println("\tpublic "+fname+" update("+fname+" obj){");
				writer.println("\t\treturn repo.save(obj);");
				writer.println("\t}");
				writer.println("\t");
				writer.println("\tpublic void deleteById("+typeId+" id){");
				writer.println("\t\trepo.deleteById(id);");
				writer.println("\t}");
				writer.println("\t");
				writer.println("}");
				writer.close();
			}
		}catch(Exception ex) {
			
		}
	}
	
	private static void writeControllers(String nfolder, File model) {
		nfolder += "controller";
		new File(nfolder).mkdir();
		String fname = model.getName().replace(".java", "");
		try {
			if(!new File(nfolder+"\\"+fname+"Controller.java").exists()) {
				PrintWriter writer = new PrintWriter(nfolder+"\\"+fname+"Controller.java", "UTF-8");
				writer.println("package "+pakage+".controller;\n");
				writer.println("import org.springframework.beans.factory.annotation.Autowired;");
				writer.println("import org.springframework.web.bind.annotation.*;");
				writer.println("import org.springframework.http.*;");
				writer.println("import org.springframework.validation.BindingResult;");
				writer.println("import org.springframework.security.core.Authentication;");
				writer.println("import javax.validation.Valid;\n");
				writer.println("import "+pakage+".domain."+fname+";");
				writer.println("import "+pakage+".services."+fname+"Service;");
				writer.println("import "+pakage+".services.MapValidationErrorService;");
				writer.println("import java.util.List;\n");
				writer.println("@RestController");
				writer.println("@RequestMapping(\"/api/"+fname.substring(0, 1).toLowerCase() + fname.substring(1)+"\")");
				writer.println("public class "+fname+"Controller {\n");

				writer.println("\t@Autowired");
				writer.println("\tprivate "+fname+"Service serv;");

				writer.println("\t@Autowired");
				writer.println("\tprivate MapValidationErrorService mapValidationErrorService;");
				
				writer.println("\t@GetMapping(\"/{id}\")");
				writer.println("\tpublic ResponseEntity<?> get(@PathVariable String id){");
				writer.println("\t\treturn new ResponseEntity<"+fname+">(serv.getOne(Long.parseLong(id)), HttpStatus.OK);");
				writer.println("\t}\n");
				writer.println("\t@GetMapping(\"/all\")");
				writer.println("\tpublic List<"+fname+"> getAll(){");
				writer.println("\t\treturn serv.findAll();");
				writer.println("\t}\n");
				writer.println("\t@RequestMapping(value = \"/save\", method = RequestMethod.POST)");
				writer.println("\tpublic ResponseEntity<?> insertOne(@Valid @RequestBody "+fname+" obj, BindingResult result, Authentication authentication) {");
				writer.println("\t\tResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);");
				writer.println("\t\tif (errorMap != null) return errorMap;");
				writer.println("\t");
				writer.println("\t\treturn new ResponseEntity<"+fname+">(serv.save(obj), HttpStatus.CREATED);");
				writer.println("\t");
				writer.println("\t}\n");
				writer.println("\t@RequestMapping(value = \"/delete/{id}\", method = RequestMethod.DELETE)");
				writer.println("\tpublic ResponseEntity<?> deleteOne(@PathVariable String id) {");
				writer.println("\t\tserv.deleteById(Long.parseLong(id));");
				writer.println("\t\treturn new ResponseEntity<String>(\""+fname+" with id: '\"+id+\"' is deleted\", HttpStatus.OK);");
				writer.println("\t}\n");
				writer.println("\t");
				writer.println("}");
				writer.close();
			}
		}catch(Exception ex) {
			
		}
	}


}
