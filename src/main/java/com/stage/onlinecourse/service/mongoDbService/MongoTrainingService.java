package com.stage.onlinecourse.service.mongoDbService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Description;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Projections;
import com.stage.onlinecourse.model.Folder;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@AllArgsConstructor
@Component
public class MongoTrainingService {
	
	@Qualifier("tainingCollection")
	public MongoCollection<Document> trainingCollection;
	
	public ResponseEntity<Document> createTraining(String trainingName,String description) {
		trainingName = trainingName.toLowerCase();
		/*TODO:
		 * controler les variables d'entrée pour eviter les failles de sécurité
		 * */
	
		if(trainingName == "" || description == "") {
			throw new RuntimeException("Les parametres name et description ne doivent pas être vides");
		}
		
		Document document = trainingCollection.find(new Document("name", trainingName)).first();
		if(document != null) throw new RuntimeException("Une formation avec le même nom existe déjà");
		
		// Squelette de la formation
		Document createdDocument = new Document("name", trainingName)
            .append("description", description)
            .append("children", new HashMap<String, Folder>());
		trainingCollection.insertOne(createdDocument);
		// Retirer le champ _id avant de retourner la formation
		document = trainingCollection.find(new Document("name", trainingName)).projection(Projections.excludeId()).first();
		
		return ResponseEntity.status(HttpStatus.CREATED).body(createdDocument);
	}

	public ResponseEntity<List<Document>> listAllTraining() {
		
		/*TODO:
		 * gerer les eventuelles failles de securite
		 * */
		
		List<Document> documents = new ArrayList<>();
		FindIterable<Document> iterable = trainingCollection.find();
		
		// Récupérer les documents
		iterable.projection(Projections.excludeId());
		MongoCursor<Document> cursor = iterable.iterator();
		while (cursor.hasNext()) {
			documents.add(cursor.next());
		}
		
		if (documents.isEmpty()) throw new RuntimeException("Aucune formation n'a été trouvée : il est possible que auccune formation ne soit actuellement enregistrée");
	
		return ResponseEntity.status(HttpStatus.OK).body(documents);
		
	}

	public ResponseEntity<Document> listTrainingInfos(String trainingName) {
		trainingName = trainingName.toLowerCase();
		/*TODO:
		 * gerer les eventuelles failles de securite
		 * */
		
		if (trainingName == "") throw new RuntimeException("Le nom cherché doit être renseigné");
		
		Document query = new Document("name", trainingName);
        Document document = trainingCollection.find(query).projection(Projections.excludeId()).first();
		
        if (document == null) throw new RuntimeException("Aucune formation avec ce nom n'a été trouvée");
        
		return ResponseEntity.status(HttpStatus.OK).body(document);
		
	}

	public ResponseEntity<Document> updateTraining(String trainingCurrentName, String newName,String newDescription){
		trainingCurrentName = trainingCurrentName.toLowerCase();
		newName = newName.toLowerCase();
		/*TODO:
		 * gerer les eventuelles failles de securite
		 * *DONE
		 * renvoyer une erreur si une des informations n'est pas renseignée
		 * renvoyer une erreur si La formation n'existe pas
		 * Renvoyer une erreur si le nouveau nom est déjà pris
		 * renvoyer une erreur si les donnee entrées sont identiques aux anciennes
		 * Enregistrer les informations et renvoyer la nouvelle formation si tout est OK
		 * */
		if(trainingCurrentName == "") throw new RuntimeException("Vous devez renseigner l'ancien nom de la formation");
		if (newName == "" || newDescription == "") throw new RuntimeException("Vous devez renseigner le nouveau nom de la formation et sa description");
		
		// verifier que la formation existe bien
        Document currentDocument = trainingCollection.find(new Document("name", trainingCurrentName)).first();
        if (currentDocument == null) throw new RuntimeException("Aucune formation avec ce nom n'a été trouvée");
		
		// verifier que le nouveau nom est libre
        Document document = trainingCollection.find(new Document("name", newName)).first();
        if(document != null) throw new RuntimeException("Il existe déjà une formation nommée "+ newName);
        
        // verififer que les donnes ne sont pas identiques 
        String trainingCurrentDescription = currentDocument.get("description").toString();
        if(trainingCurrentDescription == newDescription && trainingCurrentName == newName) {
        	throw new RuntimeException("Les donnes entrées sont identiques aux anciennes");
        }
        
        document = new Document("name", newName);
        Document updatedDocument = new Document("$set", document.append("description", newDescription));
        trainingCollection.updateOne(currentDocument, updatedDocument);
    
        // recuperer le document mis à jour
        document = trainingCollection.find(document).projection(Projections.excludeId()).first();
		return ResponseEntity.status(HttpStatus.OK).body(document);
	}

	public ResponseEntity<Document> deleteTraining(String trainingName) {
		/*TODO
		 * gerer les eventuelles failles de securite
		 * *DONE
		 * renvoyer une ereur si la formation n'existe pas
		 * suprimmer le document
		 * renvoyer le nom du document apres supression
		 * */
		if (trainingName == "") throw new RuntimeException("Vous devez renseigner le nom de la formation à supprimer");
		
		Document document = trainingCollection.find(new Document("name", trainingName)).first();
		if(document == null) throw new RuntimeException("La formation "+trainingName+" n'existe pas");
		
		trainingCollection.deleteOne(document);
		return ResponseEntity.status(HttpStatus.OK).body(new Document("name", trainingName));
	}

	public ResponseEntity<Document> addFolderToTraining(String trainingName, String folderName) {
		/*TODO
		 * gerer les eventuelles failles de securite
		 * ajouter l'id logicalDoc
		 * crééer le dossier et le renvoyer
		 * *DONE
		 * envoyer une erreur si un des paramettres en vide
		 * renvoyer une erreur si la fromation n'existe pas
		 * envoyer une erreur si le dossier existe déjà
		 * */
		if (trainingName == "" || folderName == "") throw new RuntimeException("Vous devez renseiger les noms de la formation et du dossier à créer");
		
		Document training = trainingCollection.find(new Document("name", trainingName)).first();
		if(training == null) throw new RuntimeException("La formation "+trainingName+" n'existe pas");
		
		//renvoyer une erreur si le dossier existe deja
		Document trainingChildren = (Document)training.get("children");
		Document folder = (Document)trainingChildren.get(folderName);
		if(folder != null) throw new RuntimeException("Le dossier "+folderName+" existe déjà dans cette formation");
		
		Folder newFolder = Folder.builder()
				.logicalDocId("indefini").build();
		// TODO gerer le probleme de converion du Folder en Document
		trainingChildren.append(folderName, newFolder);
		var update = new Document("$push", new Document("children", trainingChildren));
		trainingCollection.updateOne(training, update);
		
		training = trainingCollection.find(new Document("name", trainingName)).first();
		return ResponseEntity.status(HttpStatus.OK).body(training);
	}

}
