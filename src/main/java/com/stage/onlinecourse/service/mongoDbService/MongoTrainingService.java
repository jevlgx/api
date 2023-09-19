package com.stage.onlinecourse.service.mongoDbService;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Qualifier;
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
import java.util.List;


@AllArgsConstructor
@Component
public class MongoTrainingService {
	
	@Qualifier("trainingCollection")
	public MongoCollection<Document> trainingCollection;
	
	@Qualifier("folderCollection")
	public MongoCollection<Document> folderCollection;
	
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
		
		if(Training.exist(trainingName)) throw new RuntimeException("Une formation avec le même nom existe déjà");
		
		// Squelette de la formation
		Training training = Training.builder()
				.name(trainingName)
				.description(description)
				.pictureLink("")
				.build();
		Document trainingAsDocument = training.toDocument();
		trainingCollection.insertOne(trainingAsDocument);
		trainingAsDocument = trainingCollection.find(new Document("name", trainingName)).projection(Projections.excludeId()).first();
		return ResponseEntity.status(HttpStatus.CREATED).body(trainingAsDocument);
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
		 * * DONE
		 * Renvoyer une erreur si le nom de la formationn'est pas renseigné
		 * renvoyer une erreur si la formation spécifiée n'existe pas
		 * renvoyer le document si il existe
		 * */
		
		if (trainingName == "") throw new RuntimeException("Le nom cherché doit être renseigné");
		Training training = Training.getTrainingByName(trainingName);
		return ResponseEntity.status(HttpStatus.OK).body(training.toDocument());
		
	}

	public ResponseEntity<Document> updateTraining(String trainingCurrentName, String newName,String newDescription, String newPictureLink){
		trainingCurrentName = trainingCurrentName.toLowerCase();
		newName = newName.toLowerCase();
		/*TODO:
		 * gerer les eventuelles failles de securite
		 * ajouter e lien vers l'image de la formation
		 * *DONE
		 * renvoyer une erreur si une des informations n'est pas renseignée
		 * renvoyer une erreur si La formation n'existe pas
		 * Renvoyer une erreur si une formation avec le nouveau nom existe déjà
		 * Enregistrer les informations et renvoyer la nouvelle formation si tout est OK
		 * */
		if(trainingCurrentName == "") throw new RuntimeException("Vous devez renseigner l'ancien nom de la formation");
		if (newName == "" || newDescription == "") throw new RuntimeException("Vous devez renseigner le nouveau nom de la formation et sa description");
		
		Training currentTraining = Training.getTrainingByName(trainingCurrentName);
		
		if(Training.exist(newName)) throw new RuntimeException("Il existe déjà une formation nommée "+ newName);
        
		Training newTraining = Training.builder()
				.name(newName)
				.description(newDescription)
				.pictureLink(newPictureLink)
				.build();
		if(newTraining.equals(currentTraining)) throw new RuntimeException("Les donnes entrées sont identiques aux anciennes");
        
        Document updatedTraining = new Document("$set", newTraining.toDocument());
        trainingCollection.updateOne(currentTraining.toDocument(), updatedTraining);
    
		return ResponseEntity.status(HttpStatus.OK).body(newTraining.toDocument());
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
		
		Training training = Training.getTrainingByName(trainingName);
		
		trainingCollection.deleteOne(training.toDocument());
		
		return ResponseEntity.status(HttpStatus.OK).body(new Document("name", trainingName));
	}

	public ResponseEntity<Document> addFolderToTraining(String trainingName, String folderName) {
		/*TODO
		 * gerer les eventuelles failles de securite
		 * ajouter l'id logicalDoc
		 * *DONE
		 * envoyer une erreur si un des paramettres en vide
		 * renvoyer une erreur si la fromation n'existe pas
		 * envoyer une erreur si la formation contient deja un dossier avec ce nom
		 * crééer le dossier le placer dans la collection des dossiers et le renvoyer
		 * */
		if (trainingName == "" || folderName == "") throw new RuntimeException("Vous devez renseiger les noms de la formation et du dossier à créer");
		
		Training training = Training.getTrainingByName(trainingName);
		
		if(training.hasFolder(folderName)) throw new RuntimeException("La formation "+trainingName+" contient déjà un dossier nommé "+folderName);
		
		// TODO ajouter le lien vers logicalDoc
		Folder folder = Folder.builder()
				.name(folderName)
				.parentId(training.getId())
				.storageLink("")
				.build();
		Document folderTodocument = folder.toDocument();
		folderCollection.insertOne(folderTodocument);
		return ResponseEntity.status(HttpStatus.OK).body(folderTodocument);
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
		/*
		 * if(trainingName == "") throw new
		 * RuntimeException("Vous devez préciser le nom de la formation"); Document
		 * training = Training.getTrainingByName(trainingName); Document childrenList =
		 * (Document)(training.get("children")); if(childrenList.isEmpty()) throw new
		 * RuntimeException("La formation "
		 * +trainingName+" ne comporte pas encore de dossiers");
		 * 
		 * return ResponseEntity.status(HttpStatus.OK).body(childrenList);
		 */
		return null;
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
		/*
		 * if(trainingName == "") throw new
		 * RuntimeException("Vous devez préciser le nom de la formation");
		 * if(folderCurrentName == "") throw new
		 * RuntimeException("Vous devez préciser le nom du dossier à modifier");
		 * if(folderNewName == "") throw new
		 * RuntimeException("Vous devez préciser le nouveau nom du dossier");
		 * if(folderCurrentName == folderNewName) throw new
		 * RuntimeException("Le nouveau nom est le même que l'ancien");
		 * 
		 * Document training = getTraining(trainingName); Document folder =
		 * getFolder(training, folderCurrentName);
		 * 
		 * 
		 * Document document = new Document("name", trainingName); Document
		 * updatedDocument = new Document("$set", document.append("description",
		 * newDescription)); trainingCollection.updateOne(currentDocument,
		 * updatedDocument);
		 * 
		 * return ResponseEntity.status(HttpStatus.OK).body(folder);
		 */
		return null;
	}
	
}
