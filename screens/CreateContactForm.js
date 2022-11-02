import React from 'react';
import {
  View,
  TextInput,
  StyleSheet,
  TouchableOpacity,
  Text,
} from 'react-native';
import {createContact} from '../modules/Contacts';

export const CreateContactForm = () => {
  const [name, setName] = React.useState('');
  const [phoneNumber, setPhoneNumber] = React.useState(null);

  const onContactSubmit = () => {
    if (!name || !phoneNumber) {
      return;
    }
    createContact({name, phoneNumber});
  };

  return (
    <View style={styles.sectionContainer}>
      <TextInput placeholder="Name" onChangeText={setName} value={name} />
      <TextInput
        placeholder="Phone Number"
        onChange={setPhoneNumber}
        value={phoneNumber}
        maxLength={9}
        keyboardType={'numeric'}
      />
      <TouchableOpacity onPress={onContactSubmit} style={styles.button}>
        <Text style={styles.appButtonText}>Create Contact</Text>
      </TouchableOpacity>
    </View>
  );
};

const styles = StyleSheet.create({
  sectionContainer: {
    marginTop: 32,
    paddingHorizontal: 24,
  },
  button: {
    elevation: 8,
    backgroundColor: '#009688',
    borderRadius: 10,
    paddingVertical: 10,
    paddingHorizontal: 12,
  },
  appButtonText: {
    fontSize: 18,
    color: '#fff',
    fontWeight: 'bold',
    alignSelf: 'center',
    textTransform: 'uppercase',
  },
});
