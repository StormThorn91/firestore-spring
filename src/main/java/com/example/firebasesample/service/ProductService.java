package com.example.firebasesample.service;

import com.example.firebasesample.entity.Product;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

@Service
public class ProductService {
    private static final String COLLECTION_NAME = "products";

    public String saveProduct(Product product) throws ExecutionException, InterruptedException {
        Random random = new Random();
        String productId = String.format("%04d", random.nextInt(10000));

        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection(COLLECTION_NAME).document(productId).set(product);

        return collectionApiFuture.get().getUpdateTime().toString();
    }

    public List<Product> getProductDetails() throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        Iterable<DocumentReference> documentReference = dbFirestore
                .collection(COLLECTION_NAME)
                .listDocuments();

        Iterator<DocumentReference> iterator = documentReference.iterator();

        Product product = null;
        List<Product> productList = new ArrayList<>();

        while (iterator.hasNext()) {
            DocumentReference df = iterator.next();
            ApiFuture<DocumentSnapshot> future = df.get();
            DocumentSnapshot document = future.get();

            product = document.toObject(Product.class);
            productList.add(product);
        }

        return productList;

    }

    public Product getProductDetailsById(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();

        DocumentReference documentReference = dbFirestore
                .collection(COLLECTION_NAME)
                .document(id);

        ApiFuture<DocumentSnapshot> future = documentReference.get();
        DocumentSnapshot document = future.get();

        Product product = null;

        if(document.exists()) {
            product = document.toObject(Product.class);
            return product;
        }
        else {
            return null;
        }
    }

    public String updateProduct(String id, Product product) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection(COLLECTION_NAME).document(id).set(product);

        return collectionApiFuture.get().getUpdateTime().toString();
    }

    public String deleteProduct(String id) throws ExecutionException, InterruptedException {
        Firestore dbFirestore = FirestoreClient.getFirestore();
        ApiFuture<WriteResult> collectionApiFuture = dbFirestore.collection(COLLECTION_NAME).document(id).delete();

        return "Document with id " + id + " is now deleted successfully";
    }
}
