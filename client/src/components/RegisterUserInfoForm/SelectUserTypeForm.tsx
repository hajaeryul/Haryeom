import { Dispatch, SetStateAction } from 'react';
import styled from 'styled-components';
import { IUserRole } from '@/apis/user/user';

interface SelectUserTypeFormProps {
    userRole: IUserRole;
    setSelectedUserRole: Dispatch<SetStateAction<IUserRole>>;
}

const SelectUserTypeForm = ({ userRole, setSelectedUserRole }: SelectUserTypeFormProps) => {
    return (
        <StyledSelectUserTypeForm>
            <UserTypeSection>
                <SelectButton
                    onClick={() => setSelectedUserRole('TEACHER')}
                    selected={userRole === 'TEACHER'}
                >
                    <img src="/images/teacher.png" alt="teacher" />
                </SelectButton>
                <span>선생님 회원</span>
            </UserTypeSection>
            <UserTypeSection>
                <SelectButton
                    onClick={() => setSelectedUserRole('STUDENT')}
                    selected={userRole === 'STUDENT'}
                >
                    <img src="/images/student-girl.png" alt="student" />
                    <img src="/images/student-boy.png" alt="student" />
                </SelectButton>
                <span>학생 회원</span>
            </UserTypeSection>
        </StyledSelectUserTypeForm>
    );
};

const StyledSelectUserTypeForm = styled.div`
    width: 100%;
    height: 320px;
    display: flex;
    align-items: center;
    justify-content: space-evenly;
    gap: 1em;
`;

const UserTypeSection = styled.div`
    display: flex;
    flex-direction: column;
    align-items: center;

    span {
        color: ${({ theme }) => theme.DARK_BLACK};
        font-weight: bold;
    }
`;

const SelectButton = styled.button<{ selected: boolean }>`
    width: 200px;
    height: 200px;
    border-radius: 100%;
    margin-bottom: 15px;
    border: 3px solid ${({ theme, selected }) => (selected ? theme.DARK_BLACK : theme.LIGHT_BLACK)};
    background-color: ${({ theme, selected }) => (selected ? theme.PRIMARY : theme.WHITE)};

    &:hover {
        border: 3px solid ${({ theme }) => theme.DARK_BLACK};
        background-color: ${({ theme }) => theme.PRIMARY};
    }
`;

export default SelectUserTypeForm;
