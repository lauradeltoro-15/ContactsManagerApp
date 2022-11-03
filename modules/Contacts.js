import {NativeModules} from 'react-native';
const {ContactsModule} = NativeModules;

export function createContact(name, phoneNumber) {
  if (!name || !phoneNumber) return;
  ContactsModule.createContact(name, phoneNumber);
}
