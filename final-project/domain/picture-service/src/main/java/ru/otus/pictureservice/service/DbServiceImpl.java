package ru.otus.pictureservice.service;

import lombok.RequiredArgsConstructor;
import org.bson.Document;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.stereotype.Service;
import ru.otus.pictureservice.model.Picture;
import ru.otus.pictureservice.repository.PictureRepository;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class DbServiceImpl implements DbService {

	private final GridFsOperations gridFsOperations;

	private final PictureRepository pictureRepository;

	@Override
	public void initDb() throws IOException {
		cleanDb();
		pictureRepository.save(new Picture(new ClassPathResource("pictures/PuteshestvieGuivera.jpg").getInputStream(), "PuteshestvieGuivera.jpg", "image/jpeg", new Document("bookId", "1")));
		pictureRepository.save(new Picture(new ClassPathResource("pictures/FourthWar.jpg").getInputStream(), "FourthWar.jpg", "image/jpeg", new Document("bookId", "2")));
		pictureRepository.save(new Picture(new ClassPathResource("pictures/EnterpriceApplicationsPatterns.jpg").getInputStream(), "EnterpriceApplicationsPatterns.jpg", "image/jpeg", new Document("bookId", "3")));
		pictureRepository.save(new Picture(new ClassPathResource("pictures/DoctorSoto.jpg").getInputStream(), "DoctorSoto.jpg", "image/jpeg", new Document("bookId", "4")));
		pictureRepository.save(new Picture(new ClassPathResource("pictures/ZokiIBada.jpg").getInputStream(), "ZokiIBada.jpg", "image/jpeg", new Document("bookId", "5")));
		pictureRepository.save(new Picture(new ClassPathResource("pictures/RingOf_Darkness.jpg").getInputStream(), "RingOf_Darkness.jpg", "image/jpeg", new Document("bookId", "6")));
		pictureRepository.save(new Picture(new ClassPathResource("pictures/Alexander_And_TerribleDay.jpg").getInputStream(), "Alexander_And_TerribleDay.jpg", "image/jpeg", new Document("bookId", "7")));
		pictureRepository.save(new Picture(new ClassPathResource("pictures/Moscow_For_Kids.jpg").getInputStream(), "Moscow_For_Kids.jpg", "image/jpeg", new Document("bookId", "8")));
		pictureRepository.save(new Picture(new ClassPathResource("pictures/Edim_Doma.jpg").getInputStream(), "Edim_Doma.jpg", "image/jpeg", new Document("bookId", "9")));
		pictureRepository.save(new Picture(new ClassPathResource("pictures/Fen_Shui.jpg").getInputStream(), "Fen_Shui.jpg", "image/jpeg", new Document("bookId", "10")));
	}

	private void cleanDb() {
		gridFsOperations.delete(new Query());
	}
}
