package com.stage.onlinecourse.service.mongoDbService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
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
import com.stage.onlinecourse.model.Training;

import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


@AllArgsConstructor
@Component
public class MongoTrainingService {
	
	@Autowired
	@Qualifier("trainingCollection")
	public MongoCollection<Document> trainingCollection;
	
	@Autowired
	@Qualifier("folderCollection")
	public MongoCollection<Document> folderColection;
	
	private Document getTraining(String trainingName) {
		Document training = trainingCollection.find(new Document("name", trainingName)).first();
		if(training == null) throw new RuntimeException("La formation "+trainingName+" n'existe pas");
		return training;
	}
	
	private Document getFolder(Document training, String folderName) {
		Document trainingChildren = (Document)training.get("children");
		Document folder = (Document)trainingChildren.get(folderName);
		if(folder == null) throw new RuntimeException("Le dossier "+folderName+" n'existe pas dans cette formation");
		return folder;
	}
	
	public ResponseEntity<Document> createTraining(String trainingName,String description) {
		trainingName = trainingName.toLowerCase();
		/*TODO:
		 * controler les variables d'entrée pour eviter les failles de sécurité
		 * ajouter le champ pictureLink lors de la creation de la formation
		 * *DONE
		 * renvoyer une erreur si un des paramettres est vide
		 * renvoyer une erreur si la formation existe déja
		 * inserer la formation
		 * retourner la formation sans son id
		 * */
	
		if(trainingName == "" || description == "") {
			throw new RuntimeException("Les parametres name et description ne doivent pas être vides");
		}
		
		Document document = trainingCollection.find(new Document("name", trainingName)).first();
		if(document != null) throw new RuntimeException("Une formation avec le même nom existe déjà");
		
		// Squelette de la formation
		Document training = Training.builder()
				.name(trainingName)
				.description(description)
				.build().toDocument();
		trainingCollection.insertOne(training);
		// Retirer le champ _id avant de retourner la formation
		document = trainingCollection.find(new Document("name", trainingName)).projection(Projections.excludeId()).first();
		
		return ResponseEntity.status(HttpStatus.CREATED).body(training);
	}

	public ResponseEntity<List<Document>> listAllTraining() {
		
		/*TODO:
		 * gerer les eventuelles failles de securite
		 * *DONE
		 * renvoyer une erreur s'il n'y a pas de formation
		 * renvoyer la liste des formation si il y en a
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
		 * DONE
		 * Renvoyer une erreur si le nom de la formationn'est pas renseigné
		 * renvoyer une erreur si la formation spécifiée n'existe pas
		 * renvoyer le document si il existe
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
		 * Renvoyer une erreur si une formation avec le nouveau nom existe déjà
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
		 * *DONE
		 * envoyer une erreur si un des paramettres en vide
		 * renvoyer une erreur si la fromation n'existe pas
		 * envoyer une erreur si le dossier existe déjà
		 * crééer le dossier et le renvoyer
		 * */
		if (trainingName == "" || folderName == "") throw new RuntimeException("Vous devez renseiger les noms de la formation et du dossier à créer");
		
		Document training = trainingCollection.find(new Document("name", trainingName)).first();
		if(training == null) throw new RuntimeException("La formation "+trainingName+" n'existe pas");
		
		//renvoyer une erreur si le dossier existe deja
		//0000000000000Document trainingChildren = (Document)training.get("children");
		Document folder = (Document)trainingChildren.get(folderName);
		if(folder != null) throw new RuntimeException("Le dossier "+folderName+" existe déjà dans cette formation");
		
		Document newFolder = Folder.builder()
				.logicalDocId(000).build().toDocument();
		training.get("children", Document.class).append(folderName, newFolder);
		trainingCollection.replaceOne(new Document("name", trainingName), training);
		
		return ResponseEntity.status(HttpStatus.OK).body(training);
	}

	public ResponseEntity<Document> listTrainingFolders(String trainingName) {
		/*TODO
		 * gerer les eventuelles failles de securité
		 * *DONE
		 * envoyer une erreur si le parametre est vide
		 * envoyer une erreur si la formation n'existe pas
		 * renvoyer une erreur s'il n'y a pas de dossiers
		 * renvoyer la liste des dossiers si elle existe
		 * */
		if(trainingName == "") throw new RuntimeException("Vous devez préciser le nom de la formation");
		Document training = getTraining(trainingName);
		Document childrenList = (Document)(training.get("children"));
		if(childrenList.isEmpty()) throw new RuntimeException("La formation "+trainingName+" ne comporte pas encore de dossiers");
		
		return ResponseEntity.status(HttpStatus.OK).body(childrenList);
	}

	public ResponseEntity<Document> updateFolderOfTraining(String trainingName, String folderCurrentName, String folderNewName) {
		/*TODO
		 * gerer les problèmes de securité
		 * mettre à jour le dossier et le renvoyer apres insertion
		 * 
		 * *DONE
		 * renvoyer une erreur si un des paramettres est vide
		 * renvoyer une erreur si le nouveau et l'ancien nom sont identiques
		 * renvoyer une erreur si la formation n'existe pas
		 * rencoyer une erreur si le dossier n'existe pas
		 * */
		if(trainingName == "") throw new RuntimeException("Vous devez préciser le nom de la formation");
		if(folderCurrentName == "") throw new RuntimeException("Vous devez préciser le nom du dossier à modifier");
		if(folderNewName == "") throw new RuntimeException("Vous devez préciser le nouveau nom du dossier");
		if(folderCurrentName == folderNewName) throw new RuntimeException("Le nouveau nom est le même que l'ancien");
		
		Document training = getTraining(trainingName);
		Document folder = getFolder(training, folderCurrentName);
		
		
		Document document = new Document("name", trainingName);
        Document updatedDocument = new Document("$set", document.append("description", newDescription));
        trainingCollection.updateOne(currentDocument, updatedDocument);
		
		return ResponseEntity.status(HttpStatus.OK).body(folder);
	}
	
}
