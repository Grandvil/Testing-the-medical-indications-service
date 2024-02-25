import org.junit.jupiter.api.*;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import ru.netology.patient.entity.BloodPressure;
import ru.netology.patient.entity.HealthInfo;
import ru.netology.patient.entity.PatientInfo;
import ru.netology.patient.repository.PatientInfoFileRepository;
import ru.netology.patient.service.alert.SendAlertServiceImpl;
import ru.netology.patient.service.medical.MedicalService;
import ru.netology.patient.service.medical.MedicalServiceImpl;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;


public class MainTests {

    private static long suiteStartTime;
    private long testStartTime;

    @BeforeAll
    public static void initSuite() {
        System.out.println("Running StringTest");
        suiteStartTime = System.nanoTime();
    }

    @AfterAll
    public static void completeSuite() {
        System.out.println("StringTest complete: " + (System.nanoTime() - suiteStartTime));
    }

    @BeforeEach
    public void initTest() {
        System.out.println("Starting new nest");
        testStartTime = System.nanoTime();
    }

    @AfterEach
    public void finalizeTest() {
        System.out.println("Test complete: " + (System.nanoTime() - testStartTime));
    }

    @Test
    void testCheckTemperature() {
        // act
        PatientInfo patientInfo = new PatientInfo(UUID.randomUUID().toString(), "Иван", "Петров", LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80)));
        String expected = String.format("Warning, patient with id: %s, need help", patientInfo.getId());

        PatientInfoFileRepository patientInfoFileRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoFileRepository.getById(patientInfo.getId()))
                .thenReturn(patientInfo);

        SendAlertServiceImpl sendAlertService = Mockito.spy(SendAlertServiceImpl.class);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoFileRepository, sendAlertService);
        medicalService.checkTemperature(patientInfo.getId(), new BigDecimal("34.0"));

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        // assert
        Mockito.verify(sendAlertService).send(argumentCaptor.capture());
        String actualMessage = argumentCaptor.getValue();
        Assertions.assertEquals(expected, actualMessage);
    }

    @Test
    void testBloodPressure() {
        // act
        PatientInfo patientInfo = new PatientInfo(UUID.randomUUID().toString(), "Иван", "Петров", LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80)));
        String expected = String.format("Warning, patient with id: %s, need help", patientInfo.getId());

        PatientInfoFileRepository patientInfoFileRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoFileRepository.getById(patientInfo.getId()))
                .thenReturn(patientInfo);

        SendAlertServiceImpl sendAlertService = Mockito.spy(SendAlertServiceImpl.class);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoFileRepository, sendAlertService);
        medicalService.checkBloodPressure(patientInfo.getId(), new BloodPressure(140, 80));

        ArgumentCaptor<String> argumentCaptor = ArgumentCaptor.forClass(String.class);

        // assert
        Mockito.verify(sendAlertService).send(argumentCaptor.capture());
        String actualMessage = argumentCaptor.getValue();
        Assertions.assertEquals(expected, actualMessage);
    }

    @Test
    void testSendAlertService() {
        // act
        PatientInfo patientInfo = new PatientInfo(UUID.randomUUID().toString(), "Иван", "Петров", LocalDate.of(1980, 11, 26),
                new HealthInfo(new BigDecimal("36.65"), new BloodPressure(120, 80)));
        String expected = String.format("Warning, patient with id: %s, need help", patientInfo.getId());

        PatientInfoFileRepository patientInfoFileRepository = Mockito.mock(PatientInfoFileRepository.class);
        Mockito.when(patientInfoFileRepository.getById(patientInfo.getId()))
                .thenReturn(patientInfo);

        SendAlertServiceImpl sendAlertService = Mockito.spy(SendAlertServiceImpl.class);

        MedicalService medicalService = new MedicalServiceImpl(patientInfoFileRepository, sendAlertService);
        medicalService.checkTemperature(patientInfo.getId(), new BigDecimal("36.0"));
        medicalService.checkBloodPressure(patientInfo.getId(), new BloodPressure(120, 80));

        // assert
        Mockito.verify(sendAlertService, Mockito.times(0)).send(expected);
    }
}
