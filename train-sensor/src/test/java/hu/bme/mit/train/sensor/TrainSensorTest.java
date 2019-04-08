package hu.bme.mit.train.sensor;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.*;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainUser;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.sensor.TrainSensorImpl;

public class TrainSensorTest {

    TrainSensor Sensor;
    TrainUser mockUser;
    TrainController mockController;

    @Before
    public void before() {
        mockController = mock(TrainController.class);
        mockUser = mock(TrainUser.class);     
       
        Sensor = new TrainSensorImpl(mockController, mockUser);
    }

    @Test
    public void AlarmState_Lowend_fail() {
        //doNothing().when(mockUser).setAlarmState(true);
       // doNothing().when(mockUser.setAlarmState(true));

        Sensor.overrideSpeedLimit(-1);
        verify(mockUser, times(1)).setAlarmState(true);
    }


    @Test
    public void AlarmState_Lowend_success() {
        Sensor.overrideSpeedLimit(1);
        verify(mockUser, times(1)).setAlarmState(false);    
    }


    @Test
    public void AlarmState_Highend_fail() {
        Sensor.overrideSpeedLimit(501);
        verify(mockUser, times(1)).setAlarmState(true);     
    }

    @Test
    public void AlarmState_Highend_success() {
        Sensor.overrideSpeedLimit(499);
        verify(mockUser, times(1)).setAlarmState(false);  
        
    }
    @Test
    public void AlarmState_percent_success() {
        when(mockController.getReferenceSpeed()).thenReturn(10);
        Sensor.overrideSpeedLimit(4);
        verify(mockUser, times(1)).setAlarmState(true);  
        
    }
    @Test
    public void AlarmState_percent_fail() {
        when(mockController.getReferenceSpeed()).thenReturn(10);
        Sensor.overrideSpeedLimit(6);
        verify(mockUser, times(1)).setAlarmState(false);  
        
    }
}
